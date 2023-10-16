package com.vananh.ecommerce.service;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Review;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest request, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);
}
