import {Component, NgZone, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../entities/user";
import {LoginService} from "../../services/login.service";
import {Jwt} from "../../entities/jwt";

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.css']
})
export class FormLoginComponent {
  private router: Router
  private loginService: LoginService;
  protected user: User

  constructor(router: Router, loginService: LoginService) {
    this.router = router;
    this.loginService = loginService;
    this.user = new User('', '')
  }

  protected login() {
    this.loginService.signInAndGetJwt(this.user).subscribe(
      response => {
        if (response) {
          this.router.navigate(['/products'])
            .then(() => window.location.reload())
            .catch(error => { throw error })
        }
      })
  }
}
