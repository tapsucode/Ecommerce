package com.vananh.ecommerce.controller;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.exception.UserException;
import com.vananh.ecommerce.model.Rating;
import com.vananh.ecommerce.model.Review;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.request.RatingRequest;
import com.vananh.ecommerce.request.ReviewRequest;
import com.vananh.ecommerce.service.ReviewService;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

        private final UserService userService;

        private final ReviewService reviewService;

        @PostMapping("/create")
        public ResponseEntity<Review> createRating(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

            User user = userService.findUserProfileByJwt(jwt);
            Review review = reviewService.createReview(request,user);

            return new ResponseEntity<Review>(review, HttpStatus.CREATED);
        }

        @GetMapping("/product/{productId}")
        public ResponseEntity<List<Review>> getProductsRating(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException{

            User user = userService.findUserProfileByJwt(jwt);
            List<Review> reviews = reviewService.getAllReview(productId);

            return new ResponseEntity<List<Review>>(reviews,HttpStatus.CREATED);
        }
    }


