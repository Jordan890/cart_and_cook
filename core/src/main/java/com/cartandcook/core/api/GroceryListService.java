package com.cartandcook.core.api;

import com.cartandcook.core.domain.GroceryList;

import java.util.List;

public interface GroceryListService {

    GroceryList upsertGroceryList(GroceryList groceryList);
    List<GroceryList> getAllGroceryLists();
    GroceryList getGroceryListById(Long id);
    void deleteGroceryList(Long id);
}
