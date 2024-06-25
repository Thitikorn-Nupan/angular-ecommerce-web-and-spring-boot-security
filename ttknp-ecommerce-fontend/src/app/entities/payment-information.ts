export class PaymentInformation {
  public amount: number;
  public currency: string;
  public email: string;

  constructor(amount: number, currency: string, email: string) {
    this.amount = amount;
    this.currency = currency;
    this.email = email;
  }
}
