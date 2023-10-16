package com.vananh.ecommerce.repository;

import com.vananh.ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r From Review r Where r.product.id=:productId")
    public List<Review> getAllProductsRating(@Param("productId") Long productId);
}
