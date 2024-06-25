import {Customer} from "./customer";
import {Address} from "./address";
import {OrderItem} from "./order-item";
import {Order} from "./order";

export class Purchase {
  public customer!:Customer
  public shippingAddress!:Address
  public billingAddress!:Address
  public order!:Order
  public orderItems!:OrderItem[]
}
