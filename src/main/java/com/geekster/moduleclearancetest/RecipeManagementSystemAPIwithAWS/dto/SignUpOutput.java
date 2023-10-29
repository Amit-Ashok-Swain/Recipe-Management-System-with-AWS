package com.geekster.moduleclearancetest.RecipeManagementSystemAPIwithAWS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpOutput {

    private boolean signUpStatus;
    private String signUpStatusMessage;

}
