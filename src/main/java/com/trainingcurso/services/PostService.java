package com.trainingcurso.services;

import com.trainingcurso.entities.ExposureEntity;
import com.trainingcurso.entities.PostEntity;
import com.trainingcurso.entities.UserEntity;
import com.trainingcurso.models.dtos.CreatePostDto;
import com.trainingcurso.models.dtos.PostDto;
import com.trainingcurso.models.responses.PostInfoResponse;
import com.trainingcurso.repositories.ExposureRepository;
import com.trainingcurso.repositories.PostRepository;
import com.trainingcurso.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService implements PostServiceInterface {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureRepository exposureRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PostDto createPost(CreatePostDto post) {

        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(post.getExposureId());

        PostEntity postEntity = new PostEntity();

        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setExpirationDate(new Date(System.currentTimeMillis() + (post.getExpirationTime()* 60000)));

        PostEntity postSaved = postRepository.save(postEntity);

        PostDto postToReturn = mapper.map(postSaved, PostDto.class);

        return postToReturn;
    }

    @Override
    public List<PostDto> getLastPosts() {

        //create my own sql req in PostRepository
        long publicExposureId = 2;
        List<PostEntity> postEntities = postRepository.getLastPublicPosts(2, new Date(System.currentTimeMillis()));
        List<PostDto> postDtoList = new ArrayList<>();

        for (PostEntity post : postEntities){
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public PostDto getPost(long id) {

        PostEntity postEntity = postRepository.findById(id);
        PostDto  postDto = mapper.map(postEntity, PostDto.class);
        return postDto;
    }

    @Override
    public void deletePost(long id, long userId) {
        PostEntity postEntity = postRepository.findById(id);

        if (postEntity.getUser().getId() != userId){
            throw  new RuntimeException("Error: No se puede realizar esta accion");
        }
        postRepository.delete(postEntity);

    }

    @Override
    public PostDto updatePost(long id, long userId, CreatePostDto postUpdateDto) {

        PostEntity postEntity = postRepository.findById(id);
        if(postEntity.getUser().getId() != userId){
            throw  new RuntimeException("Error: No se puede realizar esta accion");
        }
        ExposureEntity exposure = exposureRepository.findById(postUpdateDto.getExposureId());
        postEntity.setExposure(exposure);
        postEntity.setTitle(postUpdateDto.getTitle());
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setExpirationDate(new Date(System.currentTimeMillis() + (postUpdateDto.getExpirationTime()*60000L)));

        PostEntity updatedPost = postRepository.save(postEntity);
        PostDto postDto = mapper.map(updatedPost, PostDto.class);
        return postDto;
    }


}
