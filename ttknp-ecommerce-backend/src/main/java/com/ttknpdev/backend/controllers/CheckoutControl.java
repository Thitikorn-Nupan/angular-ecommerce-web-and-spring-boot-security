package com.ttknpdev.backend.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.ttknpdev.backend.dto.Purchase;
import com.ttknpdev.backend.entities.PaymentInfomation;
import com.ttknpdev.backend.log.Logback;
import com.ttknpdev.backend.services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/checkout")
public class CheckoutControl {
    private Logback logback;
    private CheckoutService checkoutService;

    @Autowired
    public CheckoutControl(CheckoutService checkoutService) {
        logback = new Logback(CheckoutControl.class);
        this.checkoutService = checkoutService;
    }

    @PostMapping(value = "/purchase")
    private ResponseEntity placeOrders(@RequestBody Purchase purchase) {
        /*logback.log.warn("purchase {}\npurchase.getCustomer() {}\npurchase.getShippingAddress() {}\npurchase.purchase.getBillingAddress() {}\npurchase.purchase.getOrder().getOid() {}",purchase,
                purchase.getCustomer(),
                purchase.getShippingAddress(),
                purchase.getBillingAddress(),
                purchase.getOrder());*/
        return ResponseEntity.ok(checkoutService.placeOrders(purchase));
    }

    @PostMapping(value = "/create-payment")
    private ResponseEntity createPaymentIntent(@RequestBody PaymentInfomation paymentInfomation) throws StripeException {
        // logback.log.warn("paymentInfomation {}",paymentInfomation);
        PaymentIntent paymentIntent = checkoutService.generatePaymentIntent(paymentInfomation);
        return ResponseEntity.ok(paymentIntent.toJson());
    }
}
