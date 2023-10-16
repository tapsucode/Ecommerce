package com.vananh.ecommerce.controller;

import com.vananh.ecommerce.exception.OrderException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Order;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.response.ApiResponse;
import com.vananh.ecommerce.service.OrderService;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{

        return new ResponseEntity<>(userService.findUserProfileByJwt(jwt), HttpStatus.CREATED);
    }
}
