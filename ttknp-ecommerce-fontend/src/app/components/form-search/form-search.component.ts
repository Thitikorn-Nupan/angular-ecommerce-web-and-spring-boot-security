import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-form-search',
  templateUrl: './form-search.component.html',
  styleUrls: ['./form-search.component.css']
})
export class FormSearchComponent  {
  private router : Router

  constructor(router: Router) {
    this.router = router;
  }

  protected searchProducts(value: string){
    // go to this {path:'search/:keyword' , component:ProductsListComponent}, component
    this.router.navigateByUrl(`/search/${value}`)
      .then(() => {
        console.log('requested /search/'+value)
      })
      .catch(error => {
      throw error
    })
  }

}
