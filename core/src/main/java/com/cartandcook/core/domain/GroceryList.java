package com.cartandcook.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class GroceryList {

    private final Long id;
    private final String name;
    private final String description;
    private final List<IngredientQuantity> ingredients;

    public static GroceryList hydrate(Long id, String name, String description, List<IngredientQuantity> ingredients) {
        List<IngredientQuantity> lowerIngredients = ingredients.stream().peek(ingredient ->
                ingredient.setName(ingredient.getName().toLowerCase())).toList();
        return new GroceryList(id, name.toLowerCase(), description.toLowerCase(), lowerIngredients);
    }
}
