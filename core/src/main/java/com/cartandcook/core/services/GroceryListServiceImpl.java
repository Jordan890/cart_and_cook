package com.cartandcook.core.services;

import com.cartandcook.core.api.GroceryListRepository;
import com.cartandcook.core.api.GroceryListService;
import com.cartandcook.core.domain.GroceryList;

import java.util.List;

public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryListRepository groceryListRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    @Override
    public GroceryList upsertGroceryList(GroceryList groceryList) {
        return groceryListRepository.save(groceryList);
    }

    @Override
    public List<GroceryList> getAllGroceryLists() {
        return groceryListRepository.findAll();
    }

    @Override
    public GroceryList getGroceryListById(Long id) {
        return groceryListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));
    }

    @Override
    public void deleteGroceryList(Long id) {
        groceryListRepository.delete(id);
    }
}
