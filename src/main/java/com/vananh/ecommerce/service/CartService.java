package com.vananh.ecommerce.service;


import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Cart;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest request) throws ProductException;

    public Cart findUserCart(Long userId);
}
