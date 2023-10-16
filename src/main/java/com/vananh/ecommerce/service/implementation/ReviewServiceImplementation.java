package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Product;
import com.vananh.ecommerce.model.Rating;
import com.vananh.ecommerce.model.Review;
import com.vananh.ecommerce.model.User;
import com.vananh.ecommerce.repository.ProductRepository;
import com.vananh.ecommerce.repository.ReviewRepository;
import com.vananh.ecommerce.request.ReviewRequest;
import com.vananh.ecommerce.service.ProductService;
import com.vananh.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImplementation implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {

        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();

        review.setProduct(product);
        review.setUser(user);
        review.setReview(request.getReview());
        review.setCreateAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsRating(productId);
    }
}
