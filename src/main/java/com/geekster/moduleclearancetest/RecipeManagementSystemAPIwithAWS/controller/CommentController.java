package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.controller;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Comment;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.CommentService;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService; // Ensure that you have this dependency injected

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService; // Inject the userService dependency
    }

    @GetMapping("/{commentId}")
    public Comment getComment(@PathVariable Long commentId) {
        return commentService.findComment(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment, @RequestParam String email) {
        User currentUser = userService.getUserByEmail(email);
        return commentService.updateComment(updatedComment, commentId, currentUser);
    }

    @DeleteMapping("/{commentId}")
    public String removeComment(@PathVariable Long commentId, @RequestParam String email) {
        return userService.removeComment(commentId, email);
    }

}
