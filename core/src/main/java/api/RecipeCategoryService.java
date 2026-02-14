package api;

import domain.RecipeCategory;

import java.util.List;

public interface RecipeCategoryService {

    RecipeCategory upsertRecipeCategory(RecipeCategory category);
    List<RecipeCategory> getAllRecipeCategories();
    RecipeCategory getRecipeCategoryById(Long id);
    void deleteRecipeCategory(Long id);
}
