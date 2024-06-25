import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../entities/product";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getProductsByCategory (id : number) : Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}/product/${id}`)
  }

  getProductsByName (name : string) : Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}/product/search/${name}`)
  }

}
