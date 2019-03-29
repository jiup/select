package com.courscio.api.user.authentication;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Jiupeng Zhang
 * @since 03/25/2019
 */
public class JwtConstant {
    public static final String SECRET = "TemporarySecurityJsonWebTokenForUserAccessIssuedByTheCourscioAPICenter";
    public static final SecretKey SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    public static final long EXPIRE_IN_MILLIS = 1000 * 60 * 20; // 30 min
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final String SUBJECT = "access_token";
    public static final String ISSUER = "api.courscio.com";
}
