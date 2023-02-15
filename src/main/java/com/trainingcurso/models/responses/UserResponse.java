package com.trainingcurso.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    private long id;
    private String name;
    private String email;
    private List<PostInfoResponse> posts;
}
