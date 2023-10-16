package com.vananh.ecommerce.repository;

import com.vananh.ecommerce.model.Cart;
import com.vananh.ecommerce.model.CartItem;
import com.vananh.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci From CartItem ci Where ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
    public CartItem isCartItemExit(@Param("cart")Cart cart, @Param("product")Product product,@Param("size")String size,@Param("userId")Long userId);
}
