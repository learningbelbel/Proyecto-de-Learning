package com.trainingcurso.services;

import com.trainingcurso.entities.PostEntity;
import com.trainingcurso.entities.UserEntity;
import com.trainingcurso.models.dtos.PostDto;
import com.trainingcurso.models.dtos.UserDto;
import com.trainingcurso.repositories.PostRepository;
import com.trainingcurso.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserServiceInterface{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto user) {
        UserDto userToReturn = new UserDto();
        UserEntity userEntity = new UserEntity();

        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("Error: El email ya existe");
        }

        BeanUtils.copyProperties(user, userEntity);
        //This is to make Password more secure encoded
        userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity createdUser = userRepository.save(userEntity);
        BeanUtils.copyProperties(createdUser, userToReturn);
        return userToReturn;
    }

    @Override
    public UserDto getUser(String email) {
        //This is to RETURN NAME in the HEADERS for postman

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new  UsernameNotFoundException(email);
        }
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(userEntity,userToReturn);

        return userToReturn;
    }

    @Override
    public List<PostDto> getUserPost(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> postList = postRepository.getByUserIdOrderByCreatedDateDesc(userEntity.getId());

        List<PostDto> postDtoList = new ArrayList<>();

        for (PostEntity post: postList){
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
//    @Bean

    //This is to make login user, if user doesn't exist, we return ERROR UsernoFoundExeption
    //If user Exist we return the UserEntity comes from spring, with the email, password, and authorities, now it is empty
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null){
            throw  new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
