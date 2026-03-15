package com.cartandcook.core.api;

import com.cartandcook.core.domain.RecipeAnalysis;

public interface AiService {

    RecipeAnalysis analyzeFoodImage(byte[] image);

    RecipeAnalysis analyzeRecipeImage(byte[] image);
}

