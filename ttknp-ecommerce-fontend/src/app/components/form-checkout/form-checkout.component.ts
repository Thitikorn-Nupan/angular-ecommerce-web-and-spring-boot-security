import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CartStatusService} from "../../services/cart-status.service";
import {District} from "../../entities/district";
import {Province} from "../../entities/province";
import {FormAddressService} from "../../services/form-address.service";
import {Purchase} from "../../entities/purchase";
import {Order} from "../../entities/order";
import {OrderItem} from "../../entities/order-item";
import {Address} from "../../entities/address";
import {CheckoutService} from "../../services/checkout.service";
import {Router} from "@angular/router";

import {environment} from "../../../environments/environment";
import {PaymentInformation} from "../../entities/payment-information";

@Component({
  selector: 'app-form-checkout',
  templateUrl: './form-checkout.component.html',
  styleUrls: ['./form-checkout.component.css']
})
export class FormCheckoutComponent implements OnInit {
  declare checkoutFormGroup: FormGroup

  protected isDisabled: boolean = false; // for purchase bt
  protected totalPrice: number = 0.0
  protected totalQuantity: number = 0
  protected provinces: Province[] = []


  protected shippingAddressDistrict: District[] = []
  protected billingAddressDistrict: District[] = []

  // for using api stripe payment
  private cardElement: any
  private displayError: any = ''
  private stripe = Stripe(environment.stripePublishableKey)

  private formBuilder: FormBuilder
  private cartStatusService: CartStatusService
  private formAddressService: FormAddressService
  private checkoutService: CheckoutService
  private router: Router
  private ngZone: NgZone


  constructor(formBuilder: FormBuilder,
              cartStatusService: CartStatusService,
              formAddressService: FormAddressService,
              checkoutService: CheckoutService,
              router: Router,
              ngZone: NgZone) {
    this.formBuilder = formBuilder;
    this.cartStatusService = cartStatusService;
    this.formAddressService = formAddressService
    this.checkoutService = checkoutService;
    this.router = router;
    this.ngZone = ngZone;
  }

  ngOnInit(): void {
    this.loadCartStatus()
    this.initialCheckoutFormGroup()
    this.initialStripePaymentForm()
  }

  // mapped to form group
  onSubmit() {
    this.placeOrders()
  }


  protected copyShippingAddressToBillingAddress(event: any) {
    // get values from the shippingAddress for-req-purchase
    const shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value
    console.log(shippingAddress)
    if (event.target.checked) { // if user clicked my checkbox <input type="checkbox" (change)="copyShippingAddressToBillingAddress($event)">
      this.checkoutFormGroup.controls['billingAddress'].setValue(shippingAddress) // set billingAddress for-req-purchase (Cloning) , now billingAddress === shippingAddress
      // *** update cities
      this.billingAddressDistrict = this.shippingAddressDistrict
    } else { // means cancel clicked
      this.checkoutFormGroup.controls['billingAddress'].reset()
      // bug fix for cities (clearing values)
      this.billingAddressDistrict = [] // for empty array
    }
  }

  protected getDistricts(nameOfFormGroup: string) {
    const formGroup = this.checkoutFormGroup.get(nameOfFormGroup)! // get for-req-purchase group
    const provinceName = formGroup.value['province'] // .name! <== i don't call attribute name because i set up [value]="province.name"
    this.formAddressService.getDistrictsByProvinceName(provinceName).subscribe(
      response => {
        if (nameOfFormGroup === 'shippingAddress') {
          this.shippingAddressDistrict = response
        } else {
          this.billingAddressDistrict = response
        }
        formGroup.get('district')!.setValue(response[0]) // selected first element of option city
      })
  }

  private loadCartStatus() {
    // subscribe to cart total price (subscribe works) when new event receive than make the assignment update ui
    this.cartStatusService.totalPrice.subscribe(response => this.totalPrice = response)
    // subscribe to cart total quantity
    this.cartStatusService.totalQuantity.subscribe(response => this.totalQuantity = response)
  }

  private initialCheckoutFormGroup() {
    this.checkoutFormGroup = this.formBuilder.group({
      customer: this.formBuilder.group({
        // validate rules for for-req-purchase control // can add Validators by method name
        firstname: [''],
        lastname: [''],
        // ** initial default email if use stay at login
        email: [''], // note , Validators.email it didn't check domain name ex. abi@vsss can work
      }),
      //
      shippingAddress: this.formBuilder.group({
        street: [''],
        province: [''],
        district: [''],
        zipcode: ['']
      }),
      //
      billingAddress: this.formBuilder.group({
        street: [''],
        province: [''],
        district: [''],
        zipcode: ['']
      }),
      creditCart: this.formBuilder.group({})
    })

    //
    this.formAddressService.getProvinces().subscribe(
      response => this.provinces = response
    )


  }

  private initialStripePaymentForm() {
    // get handler to stripe element
    const element = this.stripe.elements()
    // set up options for stripe element
    const options = {
      layout: {
        type: 'tabs',
        defaultCollapsed: false,
      },
      hidePostalCode: true,
      style: {
        base: {
          iconColor: '#2f3ae8',
          color: '#000000',
          fontWeight: '500',
          fontSize: '25px'
        }
      }
    };
    // create a cart elements and hind the zip code,set style cart it means debit,credit cart type
    this.cardElement = element.create('card', options)
    // add an instance of card UI component (mapped id)
    this.cardElement.mount('#card-element')
    // ** on('change') will do when user pass card number
    this.cardElement.on('change', (event: any) => {
      console.log(event)
      // good css
      this.displayError = document.getElementById('card-errors')
      if (event.complete) {
        this.displayError.textContent = ""
      } else if (event.error) {
        this.displayError.textContent = event.error.message
      }
    })
  }

  private getPurchase() {
    // sets up all entities of purchase
    let order = new Order() //
    let purchase = new Purchase()

    purchase.customer = this.checkoutFormGroup.controls['customer'].value


    order.totalPrice = this.totalPrice
    order.totalQuantity = this.totalQuantity

    // get all cart items to array
    let cartItems = this.cartStatusService.cartItems
    let orderItems: OrderItem[] = []  // new array type OrderItem

    for (let i = 0; i < cartItems.length; i++) {
      orderItems[i] = new OrderItem(cartItems[i])
    }

    let shippingAddress: Address
    let billingAddress: Address

    // first province and district will be objects
    shippingAddress = this.checkoutFormGroup.controls['shippingAddress'].value
    billingAddress = this.checkoutFormGroup.controls['billingAddress'].value

    // convert object to class
    // dont forget you set up [value]='province.name' and [value]='district.name'
    let districtS: string = JSON.parse(JSON.stringify(shippingAddress.district))
    let provinceS: string = JSON.parse(JSON.stringify(shippingAddress.province))
    let districtB: string = JSON.parse(JSON.stringify(billingAddress.district))
    let provinceB: string = JSON.parse(JSON.stringify(billingAddress.province))


    shippingAddress.district = districtS
    shippingAddress.province = provinceS

    billingAddress.district = districtB
    billingAddress.province = provinceB

    // set up purchase for req
    purchase.shippingAddress = shippingAddress
    purchase.billingAddress = billingAddress

    purchase.order = order
    purchase.orderItems = orderItems

    return purchase
  }

  private placeOrders() {
    if (confirm('Are you already to purchase?')) {

      const purchase = this.getPurchase()
      // have to * 100 cause payment /100 first
      const amount = Math.round(this.totalPrice * 100)
      const current = "THB"
      const email = purchase.customer.email
      const paymentInformation = new PaymentInformation(amount, current, email)

    /*
    can't do this way because it's async method
     this.checkoutService.getPaymentIntent(paymentInformation).subscribe(
        response => {
          this.paymentIntent = response
          console.log(this.paymentIntent.client_secret)
        }
      )
      console.log(this.paymentIntent) // can't read*/

      // set param for confirmCardPayment(...)
      // ***
      const optionPaymentMethod = {
        payment_method: {
          card: this.cardElement, // reference the stripe element component
          // importance for getting status succeeded when credit card was worked
          billing_details: {
            email: purchase.customer.email,
            name: `${purchase.customer.firstname} ${purchase.customer.lastname}`,
            address: {
              line1: purchase.billingAddress.street,
              city: `${purchase.billingAddress.province} ${purchase.billingAddress.district}`,
              country: 'TH' , // can't thailand use only 2 char
              postal_code: purchase.billingAddress.zipcode
            }
          } // billing detail
        }  // payment method
      }

      // ***
      const optionHandlerAction = { handleActions: false}

      //
      this.checkoutService.getPaymentIntent(paymentInformation).subscribe(
        response => {
          this.stripe.confirmCardPayment(response.client_secret,optionPaymentMethod, optionHandlerAction)
            .then((result: any) => {
              console.log(result.paymentIntent)
              if (result.error) {
                alert('There was an error while processing your payment : ' + result.error.message)
                this.isDisabled = false //  close purchase if fail something
              } else {
                this.isDisabled = false
                // request place orders
                this.checkoutService.getPlaceOrders(purchase).subscribe(
                  response => {
                    alert(`Order tracking number is ${response.orderTrackingNumber}`)
                    this.clearCart()
                  }
                )
                console.log('send message to line')
              }
            }) // confirmCardPayment()
        }) // getPaymentIntent()
    }
  }

  private clearCart() {
    // clear all attributes
    this.cartStatusService.cartItems = []
    this.cartStatusService.totalQuantity.next(0)
    this.cartStatusService.totalPrice.next(0)
    // for solve the bug , ex refresh after done checkout but cart not reset
    // **** update storage
    this.cartStatusService.persistCartItems() // have to set cartItems again now will be 0
    this.checkoutFormGroup.reset() // ***
    // then redirect to default path
    this.ngZone.run(() => this.router.navigateByUrl('')).catch(error => {
      throw error
    })
  }
}
