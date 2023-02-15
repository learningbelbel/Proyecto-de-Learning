package com.trainingcurso;

import com.trainingcurso.models.dtos.UserDto;
import com.trainingcurso.models.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
@CrossOrigin("*")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Funcionando");
	}

	// This is to Encode Password
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
    }

	@Bean
	public ApplicationContextApp applicationContextApp(){
		return new ApplicationContextApp();
	}

	@Bean
	public ModelMapper mapper(){
		ModelMapper mapper = new ModelMapper();

		//this is avoid a infinity mapper from UserResponse to postsResponse;
		/*
		 *  "user": {
		 *             "id": 4,
		 *             "name": "test1",
		 *             "email": "test1@gmail.com",
		 *             "posts": null // with the process below i made return this in postman
		 *         },
		 */
		mapper.typeMap(UserDto.class, UserResponse.class).addMappings(m-> m.skip(UserResponse::setPosts));
		return mapper;
	}


}
