import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../entities/user";
import {map, Observable} from "rxjs";
import {Jwt} from "../entities/jwt";
import {District} from "../entities/district";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private httpClient : HttpClient
  private readonly baseUrl = environment.baseUrl;
  private storage : Storage = sessionStorage;
  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  signInAndGetJwt(user : User) :  Observable<boolean> {
    return  this.httpClient.post<Jwt>(`${this.baseUrl}/sign-in/jwt`,user).pipe(map(response => {
      // set items on storage
      // try to set like object
      this.storage.setItem('jwt', JSON.stringify(response));
      // this.storage.setItem('jwt', response.jwt);
      // this.storage.setItem('username', response.username);
      return true;
    }))
  }

}
