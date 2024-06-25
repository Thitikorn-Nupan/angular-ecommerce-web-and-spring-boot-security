import {Component, NgZone, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {LoginService} from "../../services/login.service";


@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {
  private router: Router
  protected disableLogin: boolean = false;
  private storage : Storage = sessionStorage;

  constructor(router: Router) {
    this.router = router;
  }

  ngOnInit(): void {
    if (this.storage.getItem('jwt') != null) {
      this.disableLogin = true
    }
  }


  protected logout() {
    this.router.navigate(['products'])
      .then(() => {
        this.storage.clear()
        window.location.reload();
      }).catch(error => {
      throw error
    })
  }
}
