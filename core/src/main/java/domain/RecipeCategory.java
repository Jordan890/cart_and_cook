package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RecipeCategory {

    Long id;
    String categoryName;
    List<Recipe> recipes;
}
