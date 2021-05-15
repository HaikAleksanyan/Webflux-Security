package com.webflux.auth.config;

public class AppConstants {
    public final static String AUTHORITIES_KEY = "roles";
    public final static String SIGNING_KEY = "scopes";
    public final static long ACCESS_TOKEN_VALIDITY_SECONDS = 500;
    public static final String TOKEN_PREFIX = "Bearer ";
}
