package com.trainingcurso.services;

import com.trainingcurso.models.dtos.PostDto;
import com.trainingcurso.models.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {

    public UserDto createUser( UserDto user);

    //This is to return the Header UserName in the headers postman
    public UserDto getUser(String email);

    public List<PostDto> getUserPost(String email);
}
