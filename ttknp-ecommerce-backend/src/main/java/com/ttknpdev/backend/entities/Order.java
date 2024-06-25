package com.ttknpdev.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;
    private String orderTrackingNumber;
    /* when send in frontend only need ,totalQuantity,totalPrice*/
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    @CreationTimestamp
    private Date dateCreated;
    // ************************************************************************************************************************
    // CascadeType. ALL is a cascading type in Hibernate that specifies that all state transitions (create, update, delete, and refresh) should be cascaded from the parent entity to the child entities (importance)
    @ManyToOne
    @JoinColumn(name = "customer_cid")
    @JsonIgnore
    private Customer customer; // fk (orders) mapped pk (customer)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_aid",referencedColumnName = "aid")
    @JsonIgnore
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_aid",referencedColumnName = "aid")
    @JsonIgnore
    private Address billingAddress;
    // *** it's not in table
    // order has order items
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

  /*  @Override
    public String toString() {
        return "Order{" +
                "billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress +
                ", customer=" + customer +
                ", dateCreated=" + dateCreated +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", orderTrackingNumber='" + orderTrackingNumber + '\'' +
                ", oid=" + oid +
                '}';
    }*/
}
