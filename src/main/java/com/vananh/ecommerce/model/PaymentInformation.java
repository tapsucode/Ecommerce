package com.vananh.ecommerce.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {

//    @Column(name = "cardholder_name", columnDefinition = "VARCHAR(255)")
    private String cardholderName;

//    @Column(name = "card_number", columnDefinition = "VARCHAR(16)")
    private String cardNumber;

//    @Column(name = "expiration_date", columnDefinition = "TIMESTAMP")
    private LocalDate expirationDate;

//    @Column(name = "cvv", columnDefinition = "VARCHAR(3)")
    private String cvv;

}
