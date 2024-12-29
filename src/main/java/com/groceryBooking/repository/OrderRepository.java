package com.groceryBooking.repository;

import com.groceryBooking.entity.GroceryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<GroceryOrder, Long> {
    List<GroceryOrder> findByUserId(Long userId);
}
