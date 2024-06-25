package com.ttknpdev.backend.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.ttknpdev.backend.dto.Purchase;
import com.ttknpdev.backend.entities.*;
import com.ttknpdev.backend.log.Logback;
import com.ttknpdev.backend.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CheckoutService {


    private CustomerRepo customerRepo;
    private Logback logback;

    @Autowired
    public CheckoutService(CustomerRepo customerRepo,
                           @Value("${STRIPE.SECRET.KEY}") String stripeSecretKey) {
        this.customerRepo = customerRepo;
        this.logback = new Logback(CheckoutService.class);
        // **** initial stripe api with secret key
        Stripe.apiKey = stripeSecretKey;
    }


    @Transactional
    // we have many repositories
    // we have to mark @Transactional for tell jpa
    // 1 transaction many repositories
    // if we do not 1 trans will be 1 repo
    public ResponsePlaceOrders placeOrders(Purchase purchase) {
        logback.log.warn("purchase {}",purchase);

        // retrieve order info from dto
        Order order = purchase.getOrder();

        // generate UUID code then set up to order object
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        logback.log.warn("order after called order.setOrderTrackingNumber(UUID) {}",order);

        // populate order either order item
        Set<OrderItem> orderItems = purchase.getOrderItems();

        orderItems.forEach(item -> {
            logback.log.warn("item.pid {}",item.getProductPid());
            order.addOrderItem(item);
        });


        // populate order either address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate order either customer
        Customer customer = purchase.getCustomer();


        String email = customer.getEmail();
        Customer customerFromDatabase = customerRepo.findByEmail(email);

        // check if customer's email is existing
        if (customerFromDatabase != null) {
            logback.log.warn("customerFromDatabase {} already exists",customerFromDatabase);
            customer = customerFromDatabase;

        }

        customer.addOrder(order);
        logback.log.warn("customer already to insert {} ",customer);

        // now customer already to query
        customerRepo.save(customer);
        /*
                Hibernate: insert into address (city,country,street,town,zip_code) values (?,?,?,?,?)
                Hibernate: insert into address (city,country,street,town,zip_code) values (?,?,?,?,?)
                Hibernate: insert into orders (billing_address_id,customer_id,date_created,last_updated,order_tracking_number,shipping_address_id,status,total_price,total_quantity) values (?,?,?,?,?,?,?,?,?)
                Hibernate: insert into order_item (image_url,order_id,product_id,quantity,unit_price) values (?,?,?,?,?)
                Hibernate: select p1_0.id,p1_0.active,p1_0.category_id,p1_0.date_created,p1_0.description,p1_0.image_url,p1_0.last_updated,p1_0.name,p1_0.sku,p1_0.unit_in_stock,p1_0.unit_price from product p1_0 where p1_0.category_id=? limit ?,?
                Hibernate: select pc1_0.id,pc1_0.category_name from product_category pc1_0 where pc1_0.id=?
                Hibernate: select count(p1_0.id) from product p1_0 where p1_0.category_id=?
        */
        return new ResponsePlaceOrders(orderTrackingNumber);
    }

    public PaymentIntent generatePaymentIntent(PaymentInfomation paymentInfomation) throws StripeException  {
        // for set types of payment intent
        List<String> paymentMethodTypes = new ArrayList<>();
        // for set params of payment intent ** object type can be many types
        Map<String, Object> params = new HashMap<>();

        paymentMethodTypes.add("card"); // other payment type as wechat_pay , ... but we use card
        // key is constant name
        params.put("payment_method_types", paymentMethodTypes);
        params.put("amount", paymentInfomation.getAmount());
        params.put("currency", paymentInfomation.getCurrency());
        // for send message to email after done
        // note card test api won auto sent to email
        // u can manual on https://dashboard.stripe.com/test/payments
        params.put("receipt_email", paymentInfomation.getEmail());

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        logback.log.warn("paymentIntent {}", paymentIntent);
        return paymentIntent;
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (it's 16 char ex, str36-sasa-fdfd56s-sds...)
        return UUID.randomUUID().toString();
    }
}
