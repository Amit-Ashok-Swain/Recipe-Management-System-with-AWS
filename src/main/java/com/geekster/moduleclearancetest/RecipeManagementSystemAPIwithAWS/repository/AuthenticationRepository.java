package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.repository;


import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.AuthenticationToken;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}