package com.trainingcurso.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestModel {

    private String name;
    private String lastName;
    private String email;
    private String password;
}
