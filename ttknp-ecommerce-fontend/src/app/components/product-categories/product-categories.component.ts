import { Component, OnInit } from '@angular/core';
import {ProductCategory} from "../../entities/product-category";
import {ProductCategoriesService} from "../../services/product-categories.service";

@Component({
  selector: 'app-product-categories',
  templateUrl: './product-categories.component.html',
  styleUrls: ['./product-categories.component.css']
})
export class ProductCategoriesComponent implements OnInit {

  protected productCategories : ProductCategory[] = [];

  private productCategoriesService : ProductCategoriesService

  constructor(productCategoriesService: ProductCategoriesService) {
    this.productCategoriesService = productCategoriesService;
  }

  ngOnInit(): void {
    this.loadProductsCategories()
  }

  private loadProductsCategories() {
    this.productCategoriesService.getProductCategories().subscribe(
      response => {
        this.productCategories = response;
        console.log(this.productCategories);
      }
    )
  }

}
