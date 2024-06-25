import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProductCategory} from "../entities/product-category";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductCategoriesService {

  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;
  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getProductCategories() : Observable<ProductCategory[]> {
    return this.httpClient.get<ProductCategory[]>(this.baseUrl+"/product-category")
  }

}
