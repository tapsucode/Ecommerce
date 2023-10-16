package com.vananh.ecommerce.service;

import com.vananh.ecommerce.exception.OrderException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.response.PaymentLinkResponse;

import java.util.Map;

public interface PaymentService {
    
    public PaymentLinkResponse createPayment(Long orderId, String jwt) throws OrderException, UserException;
}
