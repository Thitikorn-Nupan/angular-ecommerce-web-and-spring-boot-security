import { Component, OnInit } from '@angular/core';
import {CartItem} from "../../entities/cart-item";
import {CartStatusService} from "../../services/cart-status.service";

@Component({
  selector: 'app-cart-detail',
  templateUrl: './cart-detail.component.html',
  styleUrls: ['./cart-detail.component.css']
})
export class CartDetailComponent implements OnInit {

  protected totalPrice : number = 0.0
  protected totalQuantity : number = 0
  protected cartItems : CartItem[] = []
  private cartStatusService : CartStatusService

  constructor(cartStatusService: CartStatusService) {
    this.cartStatusService = cartStatusService
  }

  ngOnInit(): void {
    this.loadCartDetail()
  }

  private loadCartDetail() {
    // get a handle to the cart item
    this.cartItems = this.cartStatusService.cartItems

    // subscribe all total details
    this.cartStatusService.totalPrice.subscribe(response => this.totalPrice = response)
    this.cartStatusService.totalQuantity.subscribe(response => this.totalQuantity = response)

    // computeCart() it computes for this.totalPrice , this.totalQuantity
    // why use because we have methods for (click) angular we have to update total price,quantity
    this.cartStatusService.computeCart()
  }

  // for (click)="" angular
  protected incrementQuantity(cartItem: CartItem) {
    this.cartStatusService.addToCart(cartItem)
  }

  protected decrementQuantity(cartItem: CartItem) {
    this.cartStatusService.decrementQuantity(cartItem)
  }

  protected removeCartItem(cartItem: CartItem) {
    this.cartStatusService.removeCartItem(cartItem)
  }

}
