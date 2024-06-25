import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Province} from "../entities/province";
import {District} from "../entities/district";

@Injectable({
  providedIn: 'root'
})
export class FormAddressService {

  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  getProvinces() : Observable<Province[]> {
    return this.httpClient.get<Province[]>(`${this.baseUrl}/province`)
  }
  getDistrictsByProvinceName (name : string) : Observable<District[]> {
    return this.httpClient.get<District[]>(`${this.baseUrl}/district/${name}`)
  }

}
