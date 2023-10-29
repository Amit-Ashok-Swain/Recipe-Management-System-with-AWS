package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.dto.SignInInput;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.dto.SignUpOutput;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.AuthenticationToken;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Comment;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository.UserRepository;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.hashingUtility.PasswordEncrypter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());

            //saveAppointment the user with the new encrypted password

            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
    }


    public String signInUser(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getPassword().equals(encryptedPassword)) {
                //session should be created since the password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                return "Token has been created successfully!!!"+authToken.getTokenValue();
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }


    public String signOutUser(String email) {

        User user = userRepo.findFirstByEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }

    public String addCommentToRecipe(Long recipeId, String commentText, User currentUser) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        if (recipe != null) {
            // Create a new Comment and associate it with the current user
            Comment comment = new Comment();
            comment.setText(commentText);
            comment.setUser(currentUser);

            // Add the comment to the recipe
            recipe.getComments().add(comment);

            recipeService.createRecipe(recipe);

            return "Comment added successfully";
        } else {
            throw new EntityNotFoundException("Recipe not found");
        }
    }

    public User getUserByEmail(String email) {
        return userRepo.findFirstByEmail(email);
    }

    public List<Recipe> getAllRecipesByUser(String email) {
        User user = userRepo.findFirstByEmail(email);

        if (user != null) {
            return user.getRecipes();
        }

        // If the user is not found or has no recipes, return an empty list.
        return Collections.emptyList();
    }

    public List<Comment> getAllCommentsByUserId(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        List<Comment> comments = new ArrayList<>();

        if (user != null) {
            List<Recipe> userRecipes = user.getRecipes();

            for (Recipe recipe : userRecipes) {
                List<Comment> recipeComments = recipe.getComments();

                for (Comment comment : recipeComments) {
                    if (!comment.getUser().getId().equals(userId)) {
                        comments.add(comment);
                    }
                }
            }
        }

        return comments;
    }
    public String removeRecipe(Long recipeId, String email){
        User user = userRepo.findFirstByEmail(email);
        return recipeService.removeRecipe(recipeId,user);
    }



    public String createRecipe(Recipe recipe, String email) {
        User recipeOwner = userRepo.findFirstByEmail(email);
        recipe.setOwner(recipeOwner);
        recipeService.createRecipe(recipe);
        return "Recipe created successfully";
    }



    boolean authorizeCommentRemover(String email, Comment comment)
    {
       String commentOwnerEmail = comment.getUser().getEmail();
       String recipeOwnerEmail = comment.getRecipe().getOwner().getEmail();

       return recipeOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }

    public String removeComment(Long commentId, String email){
        Comment comment = commentService.findComment(commentId);
        if (comment!= null){
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected....Deletion is not allowed";
            }
        }else{
            return "Invalid Comment";
        }
    }
}





    // Coded by: Amit Ashok Swain
    // GitHub - amitashokswain
    // E-mail - business.amitswain@gmail.com
    // Instagram - _sanatani_hindutwa_
































/*
    public List<Recipe> getAllRecipesByUser(Long userId) {
        // Implement the logic to retrieve all recipe posts by a user
        return userRepo.findById(userId)
                .map(User::getRecipes)
                .orElse(Collections.emptyList());
    }

    public List<Comment> getAllCommentsByUser(Long userId) {
        // Implement the logic to retrieve all comments done by other users on the user's recipes
        List<Comment> comments = new ArrayList<>();
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            List<Recipe> userRecipes = user.get().getRecipes();
            for (Recipe recipe : userRecipes) {
                List<Comment> recipeComments = recipe.getComments();
                for (Comment comment : recipeComments) {
                    if (!comment.getUser().getId().equals(userId)) {
                        comments.add(comment);
                    }
                }
            }
        }
        return comments;
    }

}

 */
/*
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class UserService {
    @Autowired
    private UserRepository;

    @Autowired
    private PasswordEncoder;

    public User createUser(@Valid User user) {
        // Add custom validation logic here
        if (!isValidUserData(user)) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        // Hash the password before storing it
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (isEmailUnique(user.getEmail())) {
            // Email is unique, proceed with user creation
            return userRepository.save(user);
        } else {
            // Email is already in use, handle accordingly (e.g., throw an exception or return an error message)
            throw new IllegalStateException("Email is already in use.");
        }


        return userRepository.save(user);
    }

    private boolean isValidUserData(User user) {
        // Check if username and email are not empty
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return false;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return false;
        }

        // Validate an email format using regular expression
        if (!isValidEmail(user.getEmail())) {
            return false;
        }

        // Check if the username is unique
        if (!isUsernameUnique(user.getUsername())) {
            return false;
        }

        return true;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isUsernameUnique(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isEmpty();
    }

    public boolean isEmailUnique(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isEmpty();
    }
}

*/