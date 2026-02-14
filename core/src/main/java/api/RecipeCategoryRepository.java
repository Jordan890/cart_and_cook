package api;

import domain.RecipeCategory;

import java.util.List;
import java.util.Optional;

public interface RecipeCategoryRepository {

    RecipeCategory save(RecipeCategory category);
    Optional<RecipeCategory> findById(Long id);
    List<RecipeCategory> findAll();
    void delete(Long id);
}
