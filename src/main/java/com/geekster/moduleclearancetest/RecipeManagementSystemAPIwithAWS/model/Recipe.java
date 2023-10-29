package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.enums.RecipeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long id;

        @NotBlank(message = "Recipe Name is required")
        private String name;

        @Enumerated(EnumType.STRING)
        private RecipeType recipeType;

        @NotEmpty(message = "Ingredients are required")
        private String ingredients;

        @NotEmpty(message = "Instructions are required")
        private String instructions;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LocalDateTime recipeAddedTimeStamp;


    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;


    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments;

}


