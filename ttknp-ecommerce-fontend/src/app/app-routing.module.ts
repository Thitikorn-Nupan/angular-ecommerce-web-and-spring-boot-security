import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./components/products/products.component";
import {CartDetailComponent} from "./components/cart-detail/cart-detail.component";
import {FormCheckoutComponent} from "./components/form-checkout/form-checkout.component";
import {FormLoginComponent} from "./components/form-login/form-login.component";
import {OrdersHistoryComponent} from "./components/orders-history/orders-history.component";

const routes: Routes = [
  {path:'products' , component:ProductsComponent},
  {path:'category/:id' , component:ProductsComponent},
  {path:'search/:keyword' , component:ProductsComponent},
  {path:'cart-detail' , component:CartDetailComponent},
  {path:'checkout' , component:FormCheckoutComponent},
  {path:'login' , component:FormLoginComponent},
  {path:'orders-history' , component:OrdersHistoryComponent},
  {path:'' ,redirectTo:'/products', pathMatch:'full'}, // first cases
  {path:'**' ,redirectTo:'/products', pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
