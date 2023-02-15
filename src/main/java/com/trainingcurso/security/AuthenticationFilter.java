package com.trainingcurso.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainingcurso.ApplicationContextApp;
import com.trainingcurso.models.dtos.UserDto;
import com.trainingcurso.models.requests.UserLoginRequest;
import com.trainingcurso.services.UserServiceInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
//Make sure token is secure go to https://jwt.io/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //This is a method to Filter when user wants to login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try{//Try to Login User;

            //I said Object.mapper. read all the values comes from request, and copy in UserLoginRequestClass
            //With this I convert UserLoginRequest Class to a Java Object
            UserLoginRequest userLoginRequest = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequest.class);

            //After i convert to Java Object I try to authenticate
            //Return try of Authentication
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginRequest.getEmail(),
                    userLoginRequest.getPassword(),
                    new ArrayList<>()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    //When the authentication was Valid, we have to create the Token and return it
    @Override
    public  void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authentication)
            throws IOException, ServletException {

    //Important: make User like ((User)) to get the information
    String username = ((User) authentication.getPrincipal()).getUsername();

    //Create Token
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.TOKEN_SECRET)
                .compact();
        //Add new header to the response, this is the Header Authorize
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

        //This is not a Java bean, we can not Autowired any of the service we have created before
        //This is the reason we created Application Context Class
        UserServiceInterface userService = (UserServiceInterface) ApplicationContextApp.getBean("userService");
        UserDto userDto = userService.getUser(username);
        //with this i created a new Header
        response.addHeader("UserName", userDto.getName());
    }
}
