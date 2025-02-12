package com.ysli.personalprojectmanagementsystem.controller;

import com.ysli.personalprojectmanagementsystem.modal.User;
import com.ysli.personalprojectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @GetMapping("/api/users/profile")
//    public ResponseEntity<User> getUserProfileHandler(
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//
//        User user = userService.findUserProfileByJwt(jwt);
//        user.setPassword(null);
//
//        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/api/users/{userId}")
//    public ResponseEntity<User> findUserById(
//            @PathVariable Long userId,
//            @RequestHeader("Authorization") String jwt) throws Exception {
//
//        User user = userService.findUserById(userId);
//        user.setPassword(null);
//
//        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
//    }

}