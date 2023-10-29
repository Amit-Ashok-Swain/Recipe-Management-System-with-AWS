package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository;

import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String newEmail);
}

