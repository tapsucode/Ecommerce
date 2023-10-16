package com.vananh.ecommerce.controller;

import com.vananh.ecommerce.exception.OrderException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.response.PaymentLinkResponse;
import com.vananh.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/payment/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPayment(@PathVariable()Long orderId, @RequestHeader("Authorization")String jwt) throws UserException, OrderException {

        PaymentLinkResponse paymentLinkResponse = paymentService.createPayment(orderId,jwt);


        if (paymentLinkResponse.getOrder_url() == null){
            return new ResponseEntity<>(paymentLinkResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
    }
}
