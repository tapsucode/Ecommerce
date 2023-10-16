package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.model.OrderItem;
import com.vananh.ecommerce.repository.OrderItemRepository;
import com.vananh.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
