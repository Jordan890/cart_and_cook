package com.cartandcook.core.api;

import com.cartandcook.core.domain.RecipeAnalysis;

/**
 * Fallback AiService used when no AI provider is configured.
 */
public class DisabledAiService implements AiService {

    @Override
    public RecipeAnalysis analyzeFoodImage(byte[] image) {
        throw new AiServiceException(
                "AI functionality is not available. Set 'cartandcook.ai.provider' to enable AI features.");
    }

    @Override
    public RecipeAnalysis analyzeRecipeImage(byte[] image) {
        throw new AiServiceException(
                "AI functionality is not available. Set 'cartandcook.ai.provider' to enable AI features.");
    }
}

