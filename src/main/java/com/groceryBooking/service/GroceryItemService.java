package com.groceryBooking.service;

import com.groceryBooking.ValidatingException;
import com.groceryBooking.entity.GroceryItem;
import com.groceryBooking.repository.GroceryItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryItemService {
    GroceryItemRepository groceryItemRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    public List<GroceryItem> listGroceryItems() {
        return groceryItemRepository.findAll();
    }

    public GroceryItem getGroceryItemById(Long id) {
        List<GroceryItem> items= groceryItemRepository.findAll();
        for(GroceryItem item:items){
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public GroceryItem add(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    public GroceryItem update(Long id, GroceryItem groceryItem) {
        GroceryItem item =  groceryItemRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Item not found!"));
        item.setName(groceryItem.getName());
        item.setDescription(groceryItem.getDescription());
        item.setPrice(groceryItem.getPrice());
        item.setQuantity(groceryItem.getQuantity());
        return groceryItemRepository.save(item);
    }

    public void delete(Long id) {
        if(groceryItemRepository.findById(id).isEmpty()) {
            throw new ValidatingException("Item not found!", HttpStatus.NOT_FOUND);
        }
        groceryItemRepository.deleteById(id);
    }


}
