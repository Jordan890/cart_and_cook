package com.cartandcook.selfhosted.controller;

import api.RecipeService;
import com.cartandcook.selfhosted.contracts.RecipeRequest;
import com.cartandcook.selfhosted.contracts.RecipeResponse;
import domain.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        System.out.println("Getting all recipes");
        List<RecipeResponse> response = recipeService.getAllRecipes()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> upsertRecipe(@RequestBody RecipeRequest request) {
        Recipe recipe = new Recipe(
                request.getId(),
                request.getName(),
                request.getCategory(),
                request.getDescription(),
                request.getIngredients()
        );
        Recipe saved = recipeService.upsertRecipe(recipe);
        return ResponseEntity.ok(toResponse(saved));
    }

    // Mapper to Response DTO
    private RecipeResponse toResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId() != null ? recipe.getId() : null);
        response.setName(recipe.getName());
        response.setCategory(recipe.getCategory());
        response.setDescription(recipe.getDescription());
        response.setIngredients(recipe.getIngredients());
        return response;
    }
}
