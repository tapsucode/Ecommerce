package com.vananh.ecommerce.service;

import com.vananh.ecommerce.exception.CartItemException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Cart;
import com.vananh.ecommerce.model.CartItem;
import com.vananh.ecommerce.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
