package com.cartandcook.selfhosted.contracts;

import domain.RecipeIngredient;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private List<RecipeIngredient> ingredients;
}
