import { Component, OnInit } from '@angular/core';
import {CartStatusService} from "../../services/cart-status.service";

@Component({
  selector: 'app-cart-status',
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.css']
})
export class CartStatusComponent implements OnInit {

  protected totalPrice : number = 0.0
  protected totalQuantity : number = 0

  private cartStatusService : CartStatusService


  constructor(cartStatusService: CartStatusService) {
    this.cartStatusService = cartStatusService;
  }

  ngOnInit(): void {
    this.loadCartStatus()
  }

  private loadCartStatus() {
    // subscribe to cart total price
    // (subscribe works) when new event receive than make the assignment update ui
    this.cartStatusService.totalPrice.subscribe(response => this.totalPrice = response)
    // subscribe to cart total quantity
    this.cartStatusService.totalQuantity.subscribe(response => this.totalQuantity = response)

  }

}
