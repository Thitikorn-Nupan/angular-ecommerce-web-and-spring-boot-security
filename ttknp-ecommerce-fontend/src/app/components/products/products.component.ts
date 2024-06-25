import { Component, OnInit } from '@angular/core';
import {Product} from "../../entities/product";
import {ActivatedRoute} from "@angular/router";
import { Router } from '@angular/router';

import {ProductsService} from "../../services/products.service";
import {CartStatusService} from "../../services/cart-status.service";
import {CartItem} from "../../entities/cart-item";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  protected products: Product[] = []
  private searchMode = false
  private productService: ProductsService
  private activatedRoute: ActivatedRoute
  private cartStatusService: CartStatusService

  private router:Router
  private currentCategoryId: number = 1;
  private preventCategoryId: number = 1;
  private currentKeyword : string = '';
  private preventKeyword: string = '';


  constructor(productService: ProductsService, activatedRoute: ActivatedRoute,route:Router,cartService: CartStatusService) {
    this.productService = productService;
    this.router = route
    this.activatedRoute = activatedRoute;
    this.cartStatusService= cartService;
  }

  ngOnInit(): void {
     this.loadProducts()
  }

  private loadProducts() {
    //
    this.searchMode = this.activatedRoute.snapshot.paramMap.has('keyword')
    if (this.searchMode) {
      this.handlerSearchProducts()
    } else {
      this.handlerProducts()
    }
  }

  private handlerSearchProducts() {
    this.activatedRoute.params.subscribe(
      () => {
        this.currentKeyword = this.activatedRoute.snapshot.paramMap.get('keyword') as string
        // if I got difference keyword (old != new)
        // it means user choose next or back , no the same
        // i think it does not need try check
        // if (this.preventKeyword != this.currentKeyword) {
        //   console.log('before keyword : ' + this.preventKeyword + ' now keyword ' + this.currentKeyword)
        // }

        // ** prevent it is old value that requested
        this.preventKeyword = this.currentKeyword

        // ** query
        this.loadProductsByName()
      }) // end subscribe of activatedRoute

  }

  private handlerProducts() {
    // *** Note use => subscribe() for dynamic mean when user choose category subscribe(() => { ... }) method will work
    this.activatedRoute.params.subscribe(
      () => {
        const checkParamIsExist = this.activatedRoute.snapshot.paramMap.has('id')
        if (checkParamIsExist) {
          this.currentCategoryId = Number(this.activatedRoute.snapshot.paramMap.get('id'))
          // console.log(this.currentCategoryId)
        }
        // // ***
        // if (this.preventCategoryId != this.currentCategoryId) {
        //   console.log('before category id : ' + this.preventCategoryId + ' now category id ' + this.currentCategoryId)
        // }
        this.preventCategoryId = this.currentCategoryId
        // // ** query
        this.loadProductsByCategory()
      }) // end subscribe of activatedRoute
  }

  protected addToCart(product: Product) {
    console.log(`you gonna add ${product.name} , THB${product.price} `)
    const cartItem : CartItem = new CartItem(product)
    console.log(`make sure cart item ${cartItem.name} , THB${cartItem.price} `)
    this.cartStatusService.addToCart(cartItem) // ****
  }

  private loadProductsByCategory() {
    this.productService.getProductsByCategory(this.currentCategoryId).subscribe(
      response => {
        this.products = response;
        console.log(this.products);
      }
    )
  }

  private loadProductsByName() {
    if (this.currentKeyword.length === 0) {
      this.router.navigate(['/products']).catch(error => {
        throw error
      });
    }
    else {
      this.productService.getProductsByName(this.currentKeyword).subscribe(
        response => {
          this.products = response;
          console.log(this.products);
        }
      )
    }
  }


}
