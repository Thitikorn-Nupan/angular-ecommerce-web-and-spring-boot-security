import {CartItem} from "./cart-item";

export class OrderItem {

  public imageUrl: String
  public quantity: number
  public price: number
  public productPid: number

  constructor(cartItem: CartItem) {
    this.imageUrl = cartItem.imageUrl;
    this.quantity = cartItem.quantity;
    this.price = cartItem.price;
    this.productPid = cartItem.pid;
  }
}
