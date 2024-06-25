import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Purchase} from "../entities/purchase";
import {PaymentInformation} from "../entities/payment-information";

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getPlaceOrders(purchase: Purchase): Observable<any> {
    return this.httpClient.post<Purchase>(`${this.baseUrl}/checkout/purchase`, purchase);
  }
  getPaymentIntent(paymentInformation : PaymentInformation): Observable<any> {
    return this.httpClient.post<any>(`${this.baseUrl}/checkout/create-payment`, paymentInformation);
  }
}
