package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.controller;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.dto.SignInInput;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.dto.SignUpOutput;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Comment;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/SignUp")
    public SignUpOutput signUpUser(@RequestBody User user) {
        return userService.signUpUser(user);
    }

    @PostMapping("/SignIn")
    public String signInUser(@RequestBody SignInInput signInInput) {
        return userService.signInUser(signInInput);
    }

    @PostMapping("/SignOut")
    public String signOutUser(@RequestParam String email) {
        return userService.signOutUser(email);
    }

    @PostMapping("/{email}/AddCommentToRecipe")
    public String addCommentToRecipe(@RequestParam Long recipeId, @RequestParam String commentText, @PathVariable String email) {
        User currentUser = userService.getUserByEmail(email);
        return userService.addCommentToRecipe(recipeId, commentText, currentUser);
    }

    @DeleteMapping("/{email}/RemoveRecipe")
    public String removeRecipe(@RequestParam Long recipeId, @PathVariable String email) {
        return userService.removeRecipe(recipeId, email);
    }

    @PostMapping("/{email}/CreateRecipe")
    public String createRecipe(@RequestBody Recipe recipe, @PathVariable String email) {
        return userService.createRecipe(recipe, email);
    }

    @DeleteMapping("/{email}/RemoveComment")
    public String removeComment(@RequestParam Long commentId, @PathVariable String email) {
        return userService.removeComment(commentId, email);
    }

    @GetMapping("/users/{email}/AllRecipesByUser")
    public ResponseEntity<List<Recipe>> getAllRecipesByUser(@PathVariable String email) {
        List<Recipe> recipes = userService.getAllRecipesByUser(email);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/users/{userId}/AllCommentsByUser")
    public ResponseEntity<List<Comment>> getAllCommentsByUser(@PathVariable Long userId) {
        List<Comment> comments = userService.getAllCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }
}

