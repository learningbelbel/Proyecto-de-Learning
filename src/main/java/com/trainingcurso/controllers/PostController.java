package com.trainingcurso.controllers;

import com.trainingcurso.models.dtos.CreatePostDto;
import com.trainingcurso.models.dtos.PostDto;
import com.trainingcurso.models.dtos.UserDto;
import com.trainingcurso.models.requests.PostInfoRequest;
import com.trainingcurso.models.responses.PostInfoResponse;
import com.trainingcurso.services.PostServiceInterface;
import com.trainingcurso.services.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServiceInterface postService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserServiceInterface userService;


    @PostMapping
    public PostInfoResponse createdPost(@RequestBody PostInfoRequest postInfoRequest){

        // I need to check what user is verified to create POST, also check that authenticated user is
        // creating the post

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        CreatePostDto postDto = mapper.map(postInfoRequest, CreatePostDto.class);
        postDto.setUserEmail(email);
        PostDto postToCreate = postService.createPost(postDto);

        PostInfoResponse postToReturn = mapper.map(postToCreate, PostInfoResponse.class);
        if(postToReturn.getExpirationDate().compareTo(new Date(System.currentTimeMillis())) < 0){
            postToReturn.setIsExpired(true);
        }
        return postToReturn;
    }
    @GetMapping(path = "/last")
    public List<PostInfoResponse> getLastPosts(){

        List<PostDto> posts = postService.getLastPosts();
        List<PostInfoResponse> postListResponse = new ArrayList<>();

        for(PostDto post: posts){
            PostInfoResponse postInfoResponse  = mapper.map(post, PostInfoResponse.class);

            postListResponse.add(postInfoResponse);
        }
        return postListResponse;
    }

    @GetMapping(path = "/{id}")
    public PostInfoResponse getPost(@PathVariable("id") Long id){

        PostDto post = postService.getPost(id);
        PostInfoResponse postInfoResponse = mapper.map(post, PostInfoResponse.class);
        if (postInfoResponse.getExpirationDate().compareTo(new Date(System.currentTimeMillis())) > 0){
            postInfoResponse.setIsExpired(true);
        }
        //VALIDATE IF POST IS PRIVATE OR IF IT ALREADY EXPIRED we need to be authentication, this is reason
        //why we called Authentciation authentication
        if (postInfoResponse.getExposure().getId()==1 || postInfoResponse.getIsExpired()){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().toString();
            UserDto user = userService.getUser(email);

            //if ther userID if differetn that the post user id, we return is not validat petition
            if (user.getId() != post.getUser().getId()){
                throw new RuntimeException("Error:  no tienes permisos para realizar esta Accion");
            }
        }
        return postInfoResponse;
    }

    @DeleteMapping(path = "/{id}")
    public String deletePost(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        postService.deletePost(id, user.getId());

        return "Post Eliminado Exitosamente";
    }

    @PutMapping(path = "/{id}")
    public PostInfoResponse updatePost(@RequestBody PostInfoRequest postInfoRequest, @PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        CreatePostDto updatePostDto = mapper.map(postInfoRequest, CreatePostDto.class);

        PostDto postDto = postService.updatePost(id, user.getId(), updatePostDto);

        PostInfoResponse postInfoResponse = mapper.map(postDto, PostInfoResponse.class);
        return postInfoResponse;
    }
}
