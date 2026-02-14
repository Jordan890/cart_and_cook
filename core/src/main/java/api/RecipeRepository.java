package api;

import domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    Recipe save(Recipe recipe);
    Optional<Recipe> findById(Long id);
    List<Recipe> findAll();
    void delete(Long id);
}
