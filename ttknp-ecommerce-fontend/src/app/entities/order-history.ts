export class OrderHistory {
  public oid : string
  public orderTrackingNumber : string
  public totalPrice : number
  public totalQuantity : number
  public dateCreated : Date

  constructor(oid: string, orderTrackingNumber: string, totalPrice: number, totalQuantity: number, dateCreated: Date) {
    this.oid = oid;
    this.orderTrackingNumber = orderTrackingNumber;
    this.totalPrice = totalPrice;
    this.totalQuantity = totalQuantity;
    this.dateCreated = dateCreated;
  }
}
