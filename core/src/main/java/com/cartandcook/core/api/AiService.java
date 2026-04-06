package com.cartandcook.core.api;

import com.cartandcook.core.domain.RecipeAnalysis;

public interface AiService {

    RecipeAnalysis analyzeFoodImage(byte[] image);

    RecipeAnalysis analyzeRecipeImage(byte[] image);

    RecipeAnalysis analyzeFoodByTitle(String dishTitle);

    RecipeAnalysis analyzeFoodByTitleAndImage(String dishTitle, byte[] image);

    RecipeAnalysis analyzeRecipeByTitle(String dishTitle);

    RecipeAnalysis analyzeRecipeByTitleAndImage(String dishTitle, byte[] image);
}
