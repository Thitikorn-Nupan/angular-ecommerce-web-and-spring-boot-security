package com.ttknpdev.backend.entities;

import lombok.Data;

@Data
public class PaymentInfomation {
    private Integer amount; // have to me integer but it will change to duble
    private String currency;
    private String email; // for send a receipt to email
}
