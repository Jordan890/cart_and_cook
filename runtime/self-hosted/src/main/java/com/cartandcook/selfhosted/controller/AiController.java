package com.cartandcook.selfhosted.controller;

import com.cartandcook.core.api.AiService;
import com.cartandcook.core.domain.RecipeAnalysis;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/analyze-food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeAnalysis> analyzeFood(@RequestParam("image") MultipartFile image) throws IOException {
        RecipeAnalysis result = aiService.analyzeFoodImage(image.getBytes());
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/analyze-recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeAnalysis> analyzeRecipe(@RequestParam("image") MultipartFile image) throws IOException {
        RecipeAnalysis result = aiService.analyzeRecipeImage(image.getBytes());
        return ResponseEntity.ok(result);
    }
}

