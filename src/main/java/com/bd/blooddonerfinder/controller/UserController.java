package com.bd.blooddonerfinder.controller;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.payload.request.UserRegistrationRequest;
import com.bd.blooddonerfinder.payload.response.RestApiResponse;
import com.bd.blooddonerfinder.service.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<RestApiResponse<Users>> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
        return ResponseEntity.ok().body(userService.registerUser(registrationRequest));
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<RestApiResponse<Users>> getregisterUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.getregisterUser(id));
    }
    @PutMapping("/updateById")
    public ResponseEntity<RestApiResponse<Users>> updateregisterUser(@RequestBody UserRegistrationRequest registrationRequest){
        return ResponseEntity.ok().body(userService.updateregisterUser(registrationRequest));
    }
    @GetMapping("/getAll")
    public ResponseEntity<RestApiResponse<Users>> getregisterUsers(){
        return ResponseEntity.ok().body(userService.getregisterUsers());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestApiResponse<Users>> deleteregisterUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.deleteregisterUser(id));
    }
}
