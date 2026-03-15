package com.cartandcook.core.domain;

import java.util.List;

public class RecipeAnalysis {

    private String title;
    private List<IngredientEstimate> ingredients;
    private Integer estimatedCalories;

    public RecipeAnalysis() {
    }

    public RecipeAnalysis(String title, List<IngredientEstimate> ingredients, Integer estimatedCalories) {
        this.title = title;
        this.ingredients = ingredients;
        this.estimatedCalories = estimatedCalories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<IngredientEstimate> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEstimate> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getEstimatedCalories() {
        return estimatedCalories;
    }

    public void setEstimatedCalories(Integer estimatedCalories) {
        this.estimatedCalories = estimatedCalories;
    }
}
