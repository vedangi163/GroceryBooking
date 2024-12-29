package com.groceryBooking.controller;

import com.groceryBooking.ValidatingException;
import com.groceryBooking.entity.GroceryItem;
import com.groceryBooking.repository.GroceryItemRepository;
import com.groceryBooking.service.GroceryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groceryItem")
public class GroceryItemController {
    GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService, GroceryItemRepository groceryItemRepository) {
        this.groceryItemService = groceryItemService;
    }

    @GetMapping("/{id}")
    GroceryItem getGroceryItemById(@PathVariable Long id) {        //get one item
        GroceryItem item = groceryItemService.getGroceryItemById(id);
        if(item == null) {
            throw new ValidatingException("Not found", HttpStatus.NOT_FOUND);
        }
        return item;
    }

    @GetMapping
    List<GroceryItem> getListOfGroceryItems() {                 //get list of items
        return groceryItemService.listGroceryItems();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    GroceryItem add(@RequestBody GroceryItem groceryItem) {                  //add item
        return groceryItemService.add(groceryItem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    GroceryItem update(@PathVariable Long id, @RequestBody GroceryItem groceryItem) {    //update item
        return groceryItemService.update(id, groceryItem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {                 //delete
        groceryItemService.delete(id);
    }
}
