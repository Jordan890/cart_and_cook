package com.cartandcook.core.api;

import com.cartandcook.core.domain.RecipeAnalysis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared JSON response parsing with retry support and markdown fence stripping.
 */
public final class AiResponseParser {

    private static final Logger log = LoggerFactory.getLogger(AiResponseParser.class);
    private static final int MAX_RETRIES = 2;

    private AiResponseParser() {
    }

    /**
     * Parse the LLM text content into RecipeAnalysis.
     * On failure, calls retryCallback to get a new response and retries up to MAX_RETRIES times.
     */
    public static RecipeAnalysis parseWithRetry(String content, ObjectMapper objectMapper, RetryCallback retryCallback) {
        return parseWithRetry(content, objectMapper, retryCallback, 0);
    }

    private static RecipeAnalysis parseWithRetry(String content, ObjectMapper objectMapper,
                                                  RetryCallback retryCallback, int attempt) {
        String cleaned = cleanJsonContent(content);

        try {
            return objectMapper.readValue(cleaned, RecipeAnalysis.class);
        } catch (JsonProcessingException e) {
            if (attempt < MAX_RETRIES) {
                log.warn("Failed to parse LLM JSON response (attempt {}), retrying...", attempt + 1, e);
                String retryContent = retryCallback.sendRetry();
                return parseWithRetry(retryContent, objectMapper, retryCallback, attempt + 1);
            }
            throw new AiServiceException("Failed to parse LLM response after " + (attempt + 1) + " attempts", e);
        }
    }

    /**
     * Strip markdown code fences from LLM output.
     */
    public static String cleanJsonContent(String content) {
        String cleaned = content.strip();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        return cleaned.strip();
    }

    /**
     * Callback interface for retrying a request when JSON parsing fails.
     */
    @FunctionalInterface
    public interface RetryCallback {
        /**
         * Send a retry request and return the extracted text content from the response.
         */
        String sendRetry();
    }
}

