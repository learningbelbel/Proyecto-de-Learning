package com.trainingcurso.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInfoRequest {

    //this is the information that we need to get from postman
    private String title;
    private String content;
    private long exposureId;
    private int expirationTime;

}
