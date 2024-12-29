package com.groceryBooking.controller;

import com.groceryBooking.entity.GroceryOrder;
import com.groceryBooking.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    GroceryOrder placeOrder(@RequestBody List<Long> groceryItemIds) {
        return orderService.placeOrder(groceryItemIds);
    }

    @GetMapping
    List<GroceryOrder> getOrders() {
        return orderService.getOrders();
    }
}
