package com.courscio.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/", "/hello"})
    public String hello() {
        return "hello";
    }

    @GetMapping("/get")
    public ResponseEntity<User> get() {
        System.out.println(userService.getById());
        return new ResponseEntity<>(userService.getById(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public String post(@ModelAttribute User user) {
        System.out.println(user);
        return "done";
    }
}
