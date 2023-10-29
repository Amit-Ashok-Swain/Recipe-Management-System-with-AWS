package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.service;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Comment;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public String  addComment(Comment comment) {
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepository.save(comment);
        return "Comment has been added!!!";
    }

    public Comment findComment(Long commentId) {
        return  commentRepository.findById(commentId).orElse(null);
    }

    // Method to retrieve all comments
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Method to retrieve comments for a specific recipe
    public List<Comment> getCommentsForRecipe(Long recipeId) {
        return commentRepository.findByCommenterUserId(recipeId);
    }

    // Method to retrieve comments for a specific user
    public Comment getCommentsForUser(Long userId) {
        return commentRepository.findById(userId).orElse(null);
    }

    public Comment updateComment(Comment updatedComment, Long commentId, User currentUser) {
        Comment existingComment = commentRepository.findById(commentId).orElse(null);

        if (existingComment != null) {
            if (existingComment.getUser().equals(currentUser)) {
                // Only the comment owner can update the comment
                existingComment.setUser(updatedComment.getUser());
                return commentRepository.save(existingComment);
            } else {
                throw new IllegalStateException("You are not authorized to update this comment");
                // You can create a custom exception class (IllegalStateException) for this purpose
            }
        }
        return null;
    }


    public void removeComment(Comment comment) {
        commentRepository.delete(comment);

    }
}
