package com.trainingcurso.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostInfoResponse {

    private String id;
    private String title;
    private String content;
    private Date expirationDate;
    private Date createdDate;
    private Boolean isExpired = false;
    private UserResponse user;
    private ExposureResponse exposure;
}
