package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.exception.CartItemException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Cart;
import com.vananh.ecommerce.model.CartItem;
import com.vananh.ecommerce.model.Product;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.repository.CartItemRepository;
import com.vananh.ecommerce.repository.CartRepository;
import com.vananh.ecommerce.service.CartItemService;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {

        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        return cartItemRepository.isCartItemExit(cart,product,size,userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        User requestUser = userService.findUserById(userId);

        if (user.getId().equals(requestUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
        throw new UserException("You can't remove another users item");

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> otp = cartItemRepository.findById(cartItemId);

        if (otp.isPresent()) {
            return otp.get();
        }
        throw new CartItemException("CartItem not found with id - "+cartItemId);
    }
}
