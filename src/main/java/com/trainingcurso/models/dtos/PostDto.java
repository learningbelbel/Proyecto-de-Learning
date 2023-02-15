package com.trainingcurso.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {

    private long id;
    private String title;
    private String content;
    private Date createdDate;
    private Date expirationDate;

    //this is to create the relation between USERDTO and POSTDTO, when we send the information to our customer,
    //we will show the user that is sending post;
    private UserDto user;
    private ExposureDto exposure;
}
