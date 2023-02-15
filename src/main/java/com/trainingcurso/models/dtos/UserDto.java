package com.trainingcurso.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;

    //This is to create the relation between POSTDTO and USERTDTO, when we send the information to our customer,
    //This is a List because an user have a lot of posts;
    private List<PostDto> posts;

}
