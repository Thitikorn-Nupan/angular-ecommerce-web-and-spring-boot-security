import {Product} from "./product";

/* this cart for storing any products (assume is an array)*/
export class CartItem {

  public pid:number
  public name:String
  public imageUrl:String
  public price:number
  public quantity:number

  constructor(product : Product) {
    this.pid=product.pid;
    this.name=product.name;
    this.imageUrl=product.imageUrl;
    this.price=product.price;
    this.quantity=1;
  }

}
