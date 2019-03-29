package com.courscio.api.user.authentication;

import com.courscio.api.user.User;
import com.courscio.api.user.UserService;
import com.courscio.api.user.UserView;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author Jiupeng Zhang
 * @since 03/28/2019
 */
@RestController("authenticationController")
public class AuthenticationController {
    public static final String PATH = "/auth";

    private final UserService userService;
    private final GoogleIdTokenVerifier tokenVerifier;

    @Autowired
    public AuthenticationController(UserService userService, GoogleIdTokenVerifier tokenVerifier) {
        this.userService = userService;
        this.tokenVerifier = tokenVerifier;
    }

    @DeleteMapping(PATH)
    public ResponseEntity<?> auth(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader(JwtConstant.HEADER) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        response.setHeader(JwtConstant.HEADER, null);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(PATH)
    public ResponseEntity<?> auth(@ModelAttribute Authentication authentication, HttpServletRequest request) {
        if (authentication.email == null || authentication.token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        GoogleIdToken idToken;
        try {
            if ((idToken = tokenVerifier.verify(authentication.token)) == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token Expired");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        if (!payload.getEmail().equals(authentication.email) || !payload.getEmailVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Forbidden");
        }

        if (!isUniversityOfRochesterEmail(authentication.email)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Educational Email Required");
        }

        User user = userService.getByEmail(authentication.email);
        if (user == null) {
            user = newDefaultUser(authentication.email);
            if (!userService.add(user)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        user.setNickname(payload.get("name").toString());
        user.setFirstName(payload.get("given_name").toString());
        user.setLastName(payload.get("family_name").toString());
        user.setZip(payload.get("locale").toString());
        user.setAvatar(payload.get("picture").toString());
        user.setPassword(authentication.token);
        user.setLoginCount(user.getLoginCount() + 1);
        user.setUpdateTime(ZonedDateTime.now());

        if (!userService.updatePartialFields(user)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header(JwtConstant.HEADER, generateJwtToken(user, request)).body(UserView.from(user));
    }

    private String generateJwtToken(final User user, HttpServletRequest request) {
        return JwtConstant.TOKEN_PREFIX
                .concat(Jwts.builder()
                        .setId(user.getEmail())
                        .setIssuer(JwtConstant.ISSUER)
                        .setSubject(JwtConstant.SUBJECT)
                        .signWith(JwtConstant.SECRET_KEY)
                        .addClaims(new ImmutableMap.Builder<String, Object>()
                                .put("role", user.getType().name())
                                .put("ip", request.getRemoteAddr())
                                .put("ua", request.getHeader("user-agent"))
                                .build())
                        .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.EXPIRE_IN_MILLIS))
                        .compact());
    }

    // temporary use (role=student as default)
    private User newDefaultUser(String email) {
        User user = new User();
        user.setAvatar("default_avatar");
        user.setEmail(email);
        user.setPassword("undefined");
        user.setDateOfBirth(ZonedDateTime.now());
        user.setSex(false);
        user.setSchoolId(1L);
        user.setSchoolName("University of Rochester");
        user.setCreateTime(ZonedDateTime.now());
        user.setType(User.Type.student);
        user.setLoginCount(0);
        return user;
    }

    private boolean isUniversityOfRochesterEmail(String email) {
        return email.endsWith("rochester.edu");
    }

    private static class Authentication {
        private String email;
        private String token;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Authentication.class.getSimpleName() + "[", "]")
                    .add("email='" + email + "'")
                    .add("token='" + token + "'")
                    .toString();
        }
    }
}
