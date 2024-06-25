import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductCategoriesComponent } from './components/product-categories/product-categories.component';
import { FormSearchComponent } from './components/form-search/form-search.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';
import {HttpClientModule} from "@angular/common/http";
import { ProductsComponent } from './components/products/products.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CartDetailComponent } from './components/cart-detail/cart-detail.component';
import { FormCheckoutComponent } from './components/form-checkout/form-checkout.component';
import { LoginStatusComponent } from './components/login-status/login-status.component';
import { FormLoginComponent } from './components/form-login/form-login.component';
import { OrdersHistoryComponent } from './components/orders-history/orders-history.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductCategoriesComponent,
    FormSearchComponent,
    CartStatusComponent,
    ProductsComponent,
    CartDetailComponent,
    FormCheckoutComponent,
    LoginStatusComponent,
    FormLoginComponent,
    OrdersHistoryComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
