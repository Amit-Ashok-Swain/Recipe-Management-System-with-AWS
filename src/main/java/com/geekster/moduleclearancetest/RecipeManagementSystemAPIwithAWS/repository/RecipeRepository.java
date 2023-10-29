package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.Recipe;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByOwnerAndId(User owner, Long id);
}

