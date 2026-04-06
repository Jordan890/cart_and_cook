package com.cartandcook.core.api;

/**
 * Shared AI prompt constants used by all provider adapters.
 */
public final class AiPrompts {

  private AiPrompts() {
  }

  public static final String FOOD_IMAGE_PROMPT = """
      You are a food analysis AI. The provided image is a photo of a prepared dish.

      Analyze the image and return a JSON object describing the recipe.

      You MUST return ONLY valid JSON matching this exact schema:

      {
        "title": "string",
        "category": "string",
        "description": "string",
        "ingredients": [
          {
            "name": "string",
            "amount": number,
            "unit": "string",
            "calories": number
          }
        ],
        "estimatedCalories": number
      }

      Field definitions:
      - title: Name of the dish
      - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
      - description: A brief 1-2 sentence description of the dish
      - ingredients: List of ingredients detected in the dish
      - name: Normalized common food name (e.g. "fresh diced roma tomatoes" becomes "tomato")
      - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
      - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
      - calories: Estimated calories for that ingredient amount
      - estimatedCalories: Total calories for the entire dish (must equal the sum of all ingredient calories)

      CRITICAL unit selection rules — you MUST follow these guidelines:
      - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
      - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
      - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
      - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS, shredded cheese → 1 CUPS)
      - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
      - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP, spices → 0.5 TSP)
      - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
      - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
      - COUNT: Use ONLY for whole countable items that are not measured by weight or volume (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT, lemon → 1 COUNT)
      - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
      - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

      Ingredient detection rules:
      - Detect vegetables, meats, grains, oils, spices, and sauces
      - Use normalized common food names, not descriptions
      - Estimate realistic cooking quantities for each ingredient
      - If an ingredient is unclear, choose the most likely common cooking ingredient

      Calorie estimation rules:
      - Use standard nutritional knowledge (e.g. 1 cup cooked rice = 200 cal, 100g chicken breast = 165 cal, 1 tbsp olive oil = 120 cal)
      - estimatedCalories must equal the sum of all ingredient calories

      Strict output rules:
      - Return ONLY valid JSON
      - Do NOT include markdown, code fences, or backticks
      - Do NOT include any text outside the JSON object
      - Do NOT include explanations or commentary
      - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
      - Include ALL ingredients — do NOT stop mid-array

      If the image cannot be analyzed, return:
      {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
      """;

  public static final String RECIPE_IMAGE_PROMPT = """
      You are a food analysis AI. The provided image is a recipe page or screenshot.

      Extract the recipe information and return a JSON object.

      You MUST return ONLY valid JSON matching this exact schema:

      {
        "title": "string",
        "category": "string",
        "description": "string",
        "ingredients": [
          {
            "name": "string",
            "amount": number,
            "unit": "string",
            "calories": number
          }
        ],
        "estimatedCalories": number
      }

      Field definitions:
      - title: Name of the recipe as written at the top of the screenshotted page following the below dish title rules
      - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
      - description: A brief 1-2 sentence description of the recipe
      - ingredients: List of ingredients from the recipe
      - name: Normalized common food name (e.g. "fresh diced roma tomatoes" becomes "tomato")
      - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
      - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
      - amount: Amount exactly as listed in the recipe (e.g. 1, 2.5, 0.5)
      - calories: Estimated calories for that ingredient amount
      - estimatedCalories: Total calories for the entire recipe (must equal the sum of all ingredient calories)

      CRITICAL unit selection rules — you MUST follow these guidelines:
      - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
      - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
      - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
      - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS, shredded cheese → 1 CUPS)
      - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
      - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP, spices → 0.5 TSP)
      - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
      - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
      - COUNT: Use ONLY for whole countable items that are not measured by weight or volume (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT, lemon → 1 COUNT)
      - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
      - If the recipe text specifies a unit (e.g. "2 cups flour"), map it to the matching enum value.
      - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

      Ingredient extraction rules:
      - IMPORTANT! Determine the title first by reading the top of the page/screenshot
      - If any dish title text is visible near the top, copy that title EXACTLY as written (keep wording and order)
      - NEVER invent or infer a different title from the food photo when a readable top title exists
      - Prefer the most prominent heading near the top (H1/title-style text) as the dish name
      - Ignore site branding, author names, section labels, and navigation text when choosing the title
      - If multiple candidates exist, choose the top-most, dish-specific heading
      - If the top heading is readable, do not replace it even if ingredients or thumbnail image suggest another dish
      - Extract all ingredients listed in the recipe
      - Preserve the amounts as written in the recipe
      - Use normalized common food names, not descriptions
      - Detect vegetables, meats, grains, oils, spices, and sauces

      Calorie estimation rules:
      - Use standard nutritional knowledge (e.g. 1 cup cooked rice = 200 cal, 100g chicken breast = 165 cal, 1 tbsp olive oil = 120 cal)
      - estimatedCalories must equal the sum of all ingredient calories

      Strict output rules:
      - Return ONLY valid JSON
      - Do NOT include markdown, code fences, or backticks
      - Do NOT include any text outside the JSON object
      - Do NOT include explanations or commentary
      - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
      - Include ALL ingredients — do NOT stop mid-array

      If the image cannot be analyzed, return:
      {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
      """;

  public static final String RETRY_PROMPT = """
      Your previous response was not valid JSON.

      You MUST return ONLY a valid JSON object matching this exact schema:

      {
        "title": "string",
        "category": "string",
        "description": "string",
        "ingredients": [
          {
            "name": "string",
            "amount": number,
            "unit": "string",
            "calories": number
          }
        ],
        "estimatedCalories": number
      }

      Rules:
      - Return ONLY valid JSON
      - unit must be one of: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
      - Do NOT include markdown, code fences, or backticks
      - Do NOT include any text outside the JSON object
      - Do NOT include explanations or commentary
      - estimatedCalories must equal the sum of all ingredient calories

      If you cannot produce the result, return:
      {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
      """;

  /**
   * Build a food analysis prompt that includes the dish title as context.
   * When an image is also provided, it will be sent alongside this text prompt.
   */
  public static String foodTitlePrompt(String dishTitle) {
    return """
        You are a food analysis AI. The user has provided the name of a dish: "%s"
        %s

        Based on this information, analyze the dish and return a JSON object describing the recipe.

        You MUST return ONLY valid JSON matching this exact schema:

        {
          "title": "string",
          "category": "string",
          "description": "string",
          "ingredients": [
            {
              "name": "string",
              "amount": number,
              "unit": "string",
              "calories": number
            }
          ],
          "estimatedCalories": number
        }

        Field definitions:
        - title: Name of the dish (use the provided dish name)
        - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
        - description: A brief 1-2 sentence description of the dish
        - ingredients: List of ingredients typically used in this dish
        - name: Normalized common food name (e.g. "fresh diced roma tomatoes" becomes "tomato")
        - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
        - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
        - calories: Estimated calories for that ingredient amount
        - estimatedCalories: Total calories for the entire dish (must equal the sum of all ingredient calories)

        CRITICAL unit selection rules — you MUST follow these guidelines:
        - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
        - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
        - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
        - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS)
        - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
        - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP)
        - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
        - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
        - COUNT: Use ONLY for whole countable items (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT)
        - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
        - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

        Strict output rules:
        - Return ONLY valid JSON
        - Do NOT include markdown, code fences, or backticks
        - Do NOT include any text outside the JSON object
        - Do NOT include explanations or commentary
        - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
        - Include ALL ingredients — do NOT stop mid-array

        If the dish cannot be identified, return:
        {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(dishTitle,
            "An image of the dish has also been provided — use both the title and the image to identify the ingredients.");
  }

  /**
   * Build a food analysis prompt for text-only (no image).
   */
  public static String foodTitleOnlyPrompt(String dishTitle) {
    return """
        You are a food analysis AI. The user has provided the name of a dish: "%s"

        Based on your knowledge of this dish, return a JSON object describing a standard recipe for it.

        You MUST return ONLY valid JSON matching this exact schema:

        {
          "title": "string",
          "category": "string",
          "description": "string",
          "ingredients": [
            {
              "name": "string",
              "amount": number,
              "unit": "string",
              "calories": number
            }
          ],
          "estimatedCalories": number
        }

        Field definitions:
        - title: Name of the dish (use the provided dish name)
        - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
        - description: A brief 1-2 sentence description of the dish
        - ingredients: List of ingredients typically used in this dish
        - name: Normalized common food name (e.g. "fresh diced roma tomatoes" becomes "tomato")
        - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
        - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
        - calories: Estimated calories for that ingredient amount
        - estimatedCalories: Total calories for the entire dish (must equal the sum of all ingredient calories)

        CRITICAL unit selection rules — you MUST follow these guidelines:
        - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
        - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
        - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
        - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS)
        - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
        - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP)
        - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
        - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
        - COUNT: Use ONLY for whole countable items (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT)
        - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
        - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

        Strict output rules:
        - Return ONLY valid JSON
        - Do NOT include markdown, code fences, or backticks
        - Do NOT include any text outside the JSON object
        - Do NOT include explanations or commentary
        - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
        - Include ALL ingredients — do NOT stop mid-array

        If the dish cannot be identified, return:
        {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(dishTitle);
  }

  /**
   * Build a recipe analysis prompt that includes the dish title as context.
   * When an image is also provided, it will be sent alongside this text prompt.
   */
  public static String recipeTitlePrompt(String dishTitle) {
    return """
        You are a food analysis AI. The user has provided the name of a dish: "%s"
        An image of the recipe has also been provided — use the title to help identify the recipe and extract the ingredients from the image.

        Extract the recipe information and return a JSON object.

        You MUST return ONLY valid JSON matching this exact schema:

        {
          "title": "string",
          "category": "string",
          "description": "string",
          "ingredients": [
            {
              "name": "string",
              "amount": number,
              "unit": "string",
              "calories": number
            }
          ],
          "estimatedCalories": number
        }

        Field definitions:
        - title: Name of the recipe (prefer the dish name provided, or the title visible in the image)
        - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
        - description: A brief 1-2 sentence description of the recipe
        - ingredients: List of ingredients from the recipe
        - name: Normalized common food name (e.g. "fresh diced roma tomatoes" becomes "tomato")
        - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
        - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
        - amount: Amount exactly as listed in the recipe (e.g. 1, 2.5, 0.5)
        - calories: Estimated calories for that ingredient amount
        - estimatedCalories: Total calories for the entire recipe (must equal the sum of all ingredient calories)

        CRITICAL unit selection rules — you MUST follow these guidelines:
        - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
        - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
        - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
        - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS)
        - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
        - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP)
        - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
        - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
        - COUNT: Use ONLY for whole countable items (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT)
        - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
        - If the recipe text specifies a unit (e.g. "2 cups flour"), map it to the matching enum value.
        - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

        Ingredient extraction rules:
        - Extract all ingredients listed in the recipe
        - Preserve the amounts as written in the recipe
        - Use normalized common food names, not descriptions

        Strict output rules:
        - Return ONLY valid JSON
        - Do NOT include markdown, code fences, or backticks
        - Do NOT include any text outside the JSON object
        - Do NOT include explanations or commentary
        - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
        - Include ALL ingredients — do NOT stop mid-array

        If the image cannot be analyzed, return:
        {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(dishTitle);
  }

  /**
   * Build a recipe analysis prompt for text-only (no image).
   */
  public static String recipeTitleOnlyPrompt(String dishTitle) {
    return """
        You are a food analysis AI. The user has provided the name of a dish: "%s"

        Based on your knowledge of this dish, return a JSON object describing a standard recipe for it.

        You MUST return ONLY valid JSON matching this exact schema:

        {
          "title": "string",
          "category": "string",
          "description": "string",
          "ingredients": [
            {
              "name": "string",
              "amount": number,
              "unit": "string",
              "calories": number
            }
          ],
          "estimatedCalories": number
        }

        Field definitions:
        - title: Name of the dish (use the provided dish name)
        - category: A single-word or short food category (e.g. "seafood", "pasta", "salad", "dessert", "soup")
        - description: A brief 1-2 sentence description of the dish
        - ingredients: List of ingredients typically used in this dish
        - name: Normalized common food name
        - amount: Numeric quantity (e.g. 1, 2.5, 0.5)
        - unit: One of the following enum values ONLY: GRAMS, OUNCES, POUNDS, CUPS, TBSP, TSP, ML, LITERS, COUNT
        - calories: Estimated calories for that ingredient amount
        - estimatedCalories: Total calories for the entire dish (must equal the sum of all ingredient calories)

        CRITICAL unit selection rules — you MUST follow these guidelines:
        - GRAMS: Use for solid ingredients measured by weight (e.g. chicken breast → 200 GRAMS, cheese → 100 GRAMS, flour → 250 GRAMS)
        - OUNCES: Use for smaller weight-based measurements (e.g. cream cheese → 8 OUNCES)
        - POUNDS: Use for larger weight-based measurements (e.g. ground beef → 1 POUNDS)
        - CUPS: Use for volume-based dry or liquid ingredients (e.g. rice → 1 CUPS, milk → 0.5 CUPS, broth → 2 CUPS)
        - TBSP: Use for tablespoon-sized amounts (e.g. olive oil → 2 TBSP, soy sauce → 1 TBSP, butter → 1 TBSP)
        - TSP: Use for teaspoon-sized amounts (e.g. salt → 1 TSP, pepper → 0.5 TSP, vanilla extract → 1 TSP)
        - ML: Use for precise liquid measurements in milliliters (e.g. wine → 120 ML)
        - LITERS: Use for large liquid volumes (e.g. water → 1 LITERS, stock → 0.5 LITERS)
        - COUNT: Use ONLY for whole countable items (e.g. eggs → 2 COUNT, garlic cloves → 3 COUNT, bay leaves → 2 COUNT)
        - DO NOT default to COUNT. Most ingredients should use GRAMS, CUPS, TBSP, or TSP.
        - If unsure, prefer GRAMS for solids and CUPS for liquids over COUNT.

        Strict output rules:
        - Return ONLY valid JSON
        - Do NOT include markdown, code fences, or backticks
        - Do NOT include any text outside the JSON object
        - Do NOT include explanations or commentary
        - Keep JSON compact: use short descriptions and concise ingredient names to stay within output limits
        - Include ALL ingredients — do NOT stop mid-array

        If the dish cannot be identified, return:
        {"title":"Unknown Dish","category":"unknown","description":"","ingredients":[],"estimatedCalories":0}
        """
        .formatted(dishTitle);
  }
}
