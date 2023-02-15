package com.trainingcurso.security;

public class SecurityConstants {

    //This is a class to configure the JWT and authentication

    public static final long EXPIRATION_DATE = 864000000; // VALID BY 10 DAYS
    public static final String TOKEN_PREFIX = "Bearer "; // Type of TOKEN
    public static final String HEADER_STRING = "Authorization";// Type of Header in Postman
    public static final String SIGN_UP_URL = "/users"; //URL para el login
    //This also can be set up in the ApplicationProperties
    public static final String TOKEN_SECRET = "LMRJCL6jidyFkrTmmd6k1oIaqxZLkgde";//REFERENCE: //https://randomkeygen.com/
}
