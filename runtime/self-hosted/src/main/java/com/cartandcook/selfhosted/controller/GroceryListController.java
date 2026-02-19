package com.cartandcook.selfhosted.controller;

import com.cartandcook.core.domain.GroceryList;
import com.cartandcook.selfhosted.contracts.GroceryListRequest;
import com.cartandcook.selfhosted.contracts.GroceryListResponse;
import com.cartandcook.selfhosted.service.GroceryListServiceSpring;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grocery_list")
public class GroceryListController {

    private final GroceryListServiceSpring groceryListService;

    public GroceryListController(GroceryListServiceSpring groceryListService) {
        this.groceryListService = groceryListService;
    }

    @GetMapping
    public ResponseEntity<List<GroceryListResponse>> getAllGroceryLists() {
        List<GroceryListResponse> response = groceryListService.getAllGroceryLists()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryListResponse> getGroceryListById(@PathVariable("id") Long id) {
        GroceryList groceryList = groceryListService.getGroceryListById(id);
        if (groceryList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(groceryList));
    }

    @PostMapping
    public ResponseEntity<GroceryListResponse> upsertRecipe(@RequestBody GroceryListRequest request) {
        GroceryList groceryList = GroceryList.hydrate(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getIngredients()
        );
        GroceryList saved = groceryListService.upsertGroceryList(groceryList);
        return ResponseEntity.ok(toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable("id") Long id) {
        groceryListService.deleteGroceryList(id);
    }

    // Mapper to Response DTO
    private GroceryListResponse toResponse(GroceryList groceryList) {
        GroceryListResponse response = new GroceryListResponse();
        response.setId(groceryList.getId() != null ? groceryList.getId() : null);
        response.setName(groceryList.getName());
        response.setDescription(groceryList.getDescription());
        response.setIngredients(groceryList.getIngredients());
        return response;
    }

}
