package com.groceryBooking.service;

import com.groceryBooking.ValidatingException;
import com.groceryBooking.entity.GroceryOrder;

import com.groceryBooking.entity.User;
import com.groceryBooking.repository.OrderRepository;
import com.groceryBooking.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public GroceryOrder placeOrder(List<Long> groceryItemIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new ValidatingException("User not found!", HttpStatus.NOT_FOUND);
        }
        GroceryOrder order = new GroceryOrder();
        order.setGroceryItemsId(groceryItemIds);
        order.setUserId(user.getId());
        return orderRepository.save(order);
    }

    public List<GroceryOrder> getOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new ValidatingException("User not found!", HttpStatus.NOT_FOUND);
        }
        List<GroceryOrder> orders = orderRepository.findByUserId(user.getId());
        return orders;
    }
}
