package com.cartandcook.core.api;

import com.cartandcook.core.domain.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe upsertRecipe(Recipe recipe);
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    void deleteRecipe(Long id);
}
