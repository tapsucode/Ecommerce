package com.vananh.ecommerce.controller;

import com.vananh.ecommerce.exception.CartItemException;
import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Cart;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.request.AddItemRequest;
import com.vananh.ecommerce.response.ApiResponse;
import com.vananh.ecommerce.service.CartService;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
//@Tag(name="Cart Management",discription="find user cart,add item to cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    @GetMapping("/")
//    @Operation(discription="find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
//    @Operation(discription="add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest request,@RequestHeader("Authorization") String jwt) throws ProductException,UserException {

        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), request);

        ApiResponse apiResponse = new ApiResponse("Item add to cart",true);

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


}
