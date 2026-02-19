package com.cartandcook.selfhosted.service;

import com.cartandcook.core.api.GroceryListRepository;
import com.cartandcook.core.domain.GroceryList;
import com.cartandcook.core.services.GroceryListServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryListServiceSpring {

    private final GroceryListServiceImpl coreService;

    public GroceryListServiceSpring(GroceryListRepository groceryListRepository) {
        // inject core service using core repository
        this.coreService = new GroceryListServiceImpl(groceryListRepository);
    }

    public GroceryList upsertGroceryList(GroceryList groceryList) {
        return coreService.upsertGroceryList(groceryList);
    }

    public GroceryList getGroceryListById(Long id) {
        return coreService.getGroceryListById(id);
    }

    public List<GroceryList> getAllGroceryLists() {
        return coreService.getAllGroceryLists();
    }

    public void deleteGroceryList(Long id) {
        coreService.deleteGroceryList(id);
    }
}
