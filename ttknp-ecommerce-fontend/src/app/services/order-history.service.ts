import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {OrderHistory} from "../entities/order-history";

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {
  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getOrderHistoryByEmail(username : string,jwt : string) : Observable<OrderHistory[]> {
    // set headers in part Authorization for security in backend
    const headers = new HttpHeaders({ Authorization: `Bearer ${jwt}`} );
    return this.httpClient.get<OrderHistory[]>(`${this.baseUrl}/order/search/${username}`, {headers});
  }
}
