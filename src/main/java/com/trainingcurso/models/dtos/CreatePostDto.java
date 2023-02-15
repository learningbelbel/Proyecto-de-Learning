package com.trainingcurso.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostDto {
    // this is the class to received what customer will send us
    // this is the information customer will send us from postman;

    //DTO to create our post
    private String title;
    private String content;
    private long exposureId;
    private int expirationTime;
    private String userEmail;


}
