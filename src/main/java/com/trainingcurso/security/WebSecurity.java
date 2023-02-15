package com.trainingcurso.security;

import com.trainingcurso.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
@Configuration
public class WebSecurity   {

//IMPORTANT: examplet to change Deprecated websecurityAdapter go to https://www.appsdeveloperblog.com/migrating-from-deprecated-websecurityconfigureradapter/
    @Autowired
    UserServiceInterface userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    //This is to make http://localhost:9090/users public, without authentication necessary
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        //configure AuthenticationManagerBuilder
        //This is to say what is the service we want to use in our application and also what is the algorithm to
        //encrypt our password
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        //With this, I'm calling the AuthenticationFilter.Java Class
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users")
                .permitAll()
                //Add that all the post PUBLIC are public without authentication
                .antMatchers(HttpMethod.GET, "/posts/last")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/posts/{id}")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // add filter when user is logged in and wants to access to private places;
                .addFilter(getAuthenticationFilter(authenticationManager))
                // add filter when user wants to login
                .addFilter(new AuthenticationFilter(authenticationManager))
                .authenticationManager(authenticationManager)
                .addFilter(new AuthorizationFilter(authenticationManager))
        // With this won't be created a Session Variable on our server
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        return http.build();
    }

//    //This to change the Login root
    public AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}
