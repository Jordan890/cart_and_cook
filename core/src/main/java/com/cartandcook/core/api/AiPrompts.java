package com.cartandcook.core.api;

/**
 * Shared AI prompt constants used by all provider adapters.
 */
public final class AiPrompts {

  private AiPrompts() {
  }

  public static final String RETRY_PROMPT = """
      Return ONLY a valid JSON object with this schema:
      {"title":"string","category":"string","description":"string","ingredients":[{"name":"string","amount":number,"unit":"string","calories":number}],"estimatedCalories":number}
      unit must be: GRAMS|OUNCES|POUNDS|CUPS|TBSP|TSP|ML|LITERS|COUNT
      No markdown, no code fences, no commentary. JSON only.
      """;

  /**
   * Build a food analysis prompt for text-only (no image).
   * The user provides only a dish name and the AI generates ingredients from
   * knowledge.
   */
  public static String foodTitleOnlyPrompt(String dishTitle) {
    return """
        You are a food analysis AI. The dish is: "%s"

        Return ONLY a JSON object (no markdown, no commentary):
        {"title":"string","category":"string","description":"string","ingredients":[{"name":"string","amount":number,"unit":"string","calories":number}],"estimatedCalories":number}

        Rules:
        - unit: GRAMS|OUNCES|POUNDS|CUPS|TBSP|TSP|ML|LITERS|COUNT
        - Prefer GRAMS for solids, CUPS for liquids, TBSP/TSP for small amounts, COUNT only for whole items (eggs, cloves)
        - name: short common name (e.g. "tomato" not "fresh diced roma tomatoes")
        - estimatedCalories = sum of all ingredient calories
        - Include ALL ingredients, keep JSON compact

        If unknown: {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(dishTitle);
  }

  /**
   * Build a recipe analysis prompt from OCR-extracted text.
   * The user uploaded a recipe image, Tesseract extracted the text, and now
   * the AI parses the extracted text into structured recipe JSON.
   */
  public static String recipeTextPrompt(String extractedText) {
    return """
        You are a food analysis AI. Parse this OCR-extracted recipe text into JSON.

        --- OCR TEXT ---
        %s
        --- END ---

        Return ONLY a JSON object (no markdown, no commentary):
        {"title":"string","category":"string","description":"string","ingredients":[{"name":"string","amount":number,"unit":"string","calories":number}],"estimatedCalories":number}

        Rules:
        - unit: GRAMS|OUNCES|POUNDS|CUPS|TBSP|TSP|ML|LITERS|COUNT
        - Map recipe units to enum (e.g. "2 cups" → CUPS, "1 tsp" → TSP)
        - Prefer GRAMS for solids, CUPS for liquids, COUNT only for whole items
        - name: short common name
        - calories: estimate per ingredient, estimatedCalories = sum
        - Extract ALL ingredients from the text, keep JSON compact
        - If garbled OCR, interpret as best as possible

        If unparseable: {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(extractedText);
  }
}
