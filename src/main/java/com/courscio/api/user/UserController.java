package com.courscio.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jiupeng Zhang
 * @since 03/07/2019
 */
@RestController("userController")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("authentication.principal.get(\"id\").toString().equals(\"\" + #id)")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(UserView.from(userService.getById(id)), HttpStatus.OK);
    }
}
