package com.cartandcook.core.domain;

public class IngredientEstimate {

    private String name;
    private String amount;
    private Integer calories;

    public IngredientEstimate() {
    }

    public IngredientEstimate(String name, String amount, Integer calories) {
        this.name = name;
        this.amount = amount;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}

