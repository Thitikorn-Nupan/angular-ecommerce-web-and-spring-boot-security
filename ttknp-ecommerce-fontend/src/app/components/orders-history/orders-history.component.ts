import { Component, OnInit } from '@angular/core';
import {OrderHistory} from "../../entities/order-history";
import {OrderHistoryService} from "../../services/order-history.service";
import {Jwt} from "../../entities/jwt";

@Component({
  selector: 'app-orders-history',
  templateUrl: './orders-history.component.html',
  styleUrls: ['./orders-history.component.css']
})
export class OrdersHistoryComponent implements OnInit {
  protected orderHistory : OrderHistory[] = []
  private orderHistoryService : OrderHistoryService
  private storage : Storage = sessionStorage;

  constructor(orderHistoryService: OrderHistoryService) {
    this.orderHistoryService = orderHistoryService;
  }

  ngOnInit(): void {
    this.loadOrderHistory()
  }

  private loadOrderHistory() {
    // read user's email form browser storage
    const jwtObj : Jwt = JSON.parse(this.storage.getItem('jwt')!);
    this.orderHistoryService.getOrderHistoryByEmail(jwtObj.username,jwtObj.jwt).subscribe(
      response =>  this.orderHistory = response
    )
  }
}
