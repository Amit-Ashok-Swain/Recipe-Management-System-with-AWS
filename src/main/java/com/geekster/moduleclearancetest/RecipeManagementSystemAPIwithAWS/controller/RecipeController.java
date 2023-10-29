package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.controller;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.RecipeService;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService; // Ensure that you have this dependency injected

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService; // Inject the userService dependency
    }


    @GetMapping("/{recipeId}")
    public Recipe getRecipe(@PathVariable Long recipeId) {
        return recipeService.findRecipe(recipeId);
    }

    @GetMapping
    public Iterable<Recipe>getAllRecipes()
    {
        return recipeService.getRecipes();
    }

    @PutMapping("/{recipeId}")
    public String updateRecipe(@PathVariable Long recipeId, @RequestBody Recipe updatedRecipe, @RequestParam String email) {
        User currentUser = userService.getUserByEmail(email);
        return recipeService.updateRecipe(recipeId, updatedRecipe, currentUser);
    }

    @PostMapping
    public String createRecipe(@RequestBody Recipe recipe, @RequestParam String email) {
        return userService.createRecipe(recipe, email);
    }

    @DeleteMapping("/{recipeId}")
    public String removeRecipe(@PathVariable Long recipeId, @RequestParam String email) {
        return userService.removeRecipe(recipeId, email);
    }
}
