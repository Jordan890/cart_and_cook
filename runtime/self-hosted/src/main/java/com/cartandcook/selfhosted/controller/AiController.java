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
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/analyze-food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeAnalysis> analyzeFood(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "title", required = false) String title) throws IOException {

        RecipeAnalysis result;
        boolean hasImage = image != null && !image.isEmpty();
        boolean hasTitle = title != null && !title.isBlank();

        if (hasTitle && hasImage) {
            result = aiService.analyzeFoodByTitleAndImage(title.trim(), image.getBytes());
        } else if (hasTitle) {
            result = aiService.analyzeFoodByTitle(title.trim());
        } else if (hasImage) {
            result = aiService.analyzeFoodImage(image.getBytes());
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/analyze-recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeAnalysis> analyzeRecipe(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "title", required = false) String title) throws IOException {

        RecipeAnalysis result;
        boolean hasImage = image != null && !image.isEmpty();
        boolean hasTitle = title != null && !title.isBlank();

        if (hasTitle && hasImage) {
            result = aiService.analyzeRecipeByTitleAndImage(title.trim(), image.getBytes());
        } else if (hasTitle) {
            result = aiService.analyzeRecipeByTitle(title.trim());
        } else if (hasImage) {
            result = aiService.analyzeRecipeImage(image.getBytes());
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
