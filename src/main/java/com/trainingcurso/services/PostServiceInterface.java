package com.trainingcurso.services;

import com.trainingcurso.models.dtos.CreatePostDto;
import com.trainingcurso.models.dtos.PostDto;

import java.util.List;

public interface PostServiceInterface {

    public PostDto createPost(CreatePostDto post);

    public List<PostDto> getLastPosts();
    public PostDto getPost(long id);
    public void deletePost(long id, long userId);

    public PostDto updatePost(long id, long userId, CreatePostDto postUpdateDto);

}
