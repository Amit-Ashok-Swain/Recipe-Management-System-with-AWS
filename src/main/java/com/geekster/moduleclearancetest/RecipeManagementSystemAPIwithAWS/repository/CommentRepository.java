package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCommenterUserId(Long commenterUserId);

}

