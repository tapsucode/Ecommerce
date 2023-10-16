package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Product;
import com.vananh.ecommerce.model.Rating;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.repository.RatingRepository;
import com.vananh.ecommerce.request.RatingRequest;
import com.vananh.ecommerce.service.ProductService;
import com.vananh.ecommerce.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;
    private ProductService productService;


    @Override
    public Rating createRating(RatingRequest request, User user) throws ProductException {

        Product product = productService.findProductById(request.getProductId());

        Rating rating = new Rating();

        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(request.getRating());
        rating.setCreateAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
