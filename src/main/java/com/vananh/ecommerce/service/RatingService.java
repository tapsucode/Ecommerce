package com.vananh.ecommerce.service;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Rating;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest request, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);


}
