package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public void createRecipe(Recipe recipe) {
        recipe.setRecipeAddedTimeStamp(LocalDateTime.now());
         recipeRepository.save(recipe);
    }

    public Recipe findRecipe(Long recipeId){
       return recipeRepository.findById(recipeId).orElse(null);
    }

    public String updateRecipe(Long recipeId, Recipe updatedRecipe, User currentUser) {
        Recipe existingRecipe = recipeRepository.findById(recipeId).orElse(null);

        if (existingRecipe != null && existingRecipe.getOwner().equals(currentUser)) {
            // Only the post-owner can update the post
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            recipeRepository.save(existingRecipe);

            return "Recipe updated successfully";
        } else {
            throw new IllegalStateException("You are not authorized to update this recipe");
        }
    }

    public String removeRecipe(Long recipeId, User user) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if(recipe!=null && recipe.getOwner().equals(user))
        {
            recipeRepository.deleteById(recipeId);
            return "Recipe removed successfully!!!";
        }
        else if(recipe == null)
        {
            return "Recipe to be deleted does not exist";
        }
        else{
            return "Unauthorized delete detected .... Deletion not allowed";
        }
    }



    public boolean validateRecipe(Recipe recipe){
        return (recipe!=null && recipeRepository.existsById(recipe.getId()));
    }



    public Recipe getRecipeById(Long recipeId) {

        return recipeRepository.findById(recipeId).orElse(null);
    }

    public Iterable<Recipe> getRecipes(){
       return recipeRepository.findAll();
    }


}

