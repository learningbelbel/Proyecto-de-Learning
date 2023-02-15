package com.trainingcurso.controllers;

import com.trainingcurso.models.dtos.PostDto;
import com.trainingcurso.models.dtos.UserDto;
import com.trainingcurso.models.requests.UserRequestModel;
import com.trainingcurso.models.responses.PostInfoResponse;
import com.trainingcurso.models.responses.UserResponse;
import com.trainingcurso.services.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public UserResponse getUser(){
        // here we will return the informations's user is authenticated;

        // Verify user is authenticated;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        //look for email in user service
        UserDto userDto = userService.getUser(email);

        UserResponse userToReturn = mapper.map(userDto, UserResponse.class);

        return userToReturn;
    }
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequestModel userRequestedInfo){
        UserResponse userToReturn = new UserResponse();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userRequestedInfo, userDto);

        UserDto userStorage = userService.createUser(userDto);

        BeanUtils.copyProperties(userStorage,userToReturn);

        return userToReturn;
    }

    @GetMapping(path = "/posts")
    public List<PostInfoResponse> getPosts(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        List<PostDto> post  = userService.getUserPost(email);
        List<PostInfoResponse> postResponse = new ArrayList<>();
        //convertir list de POSTDTO a POSTRESPONSE

        for (PostDto posts : post){
            PostInfoResponse postInfoResponse = mapper.map(posts, PostInfoResponse.class);
            if(postInfoResponse.getExpirationDate().compareTo(new Date(System.currentTimeMillis())) < 0){
                postInfoResponse.setIsExpired(true);
            }
            postResponse.add(postInfoResponse);

        }
        return postResponse;

    }



}
