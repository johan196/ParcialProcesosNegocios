package org.Primerparcialapp.controller;

import org.Primerparcialapp.Utils.ApiResponse;
import org.Primerparcialapp.Utils.Constants;
import org.Primerparcialapp.model.User;
import org.Primerparcialapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;
    Map data = new HashMap<>();

    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable Long id) {
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_FOUND, userService.getUser(id));
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity saveUser(@RequestBody User user) {
        Boolean userResp = userService.createUser(user);

        if (userResp) {
            apiResponse = new ApiResponse(Constants.REGISTER_CREATED, "");
            return new ResponseEntity(apiResponse, HttpStatus.CREATED);
        }
        apiResponse = new ApiResponse(Constants.REGISTER_BAD, user);
        return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity findAllUser() {
        try {
            apiResponse = new ApiResponse(Constants.REGISTER_LIST, userService.allUsers());
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            apiResponse = new ApiResponse(Constants.REGISTER_NOT_FOUND, "");
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }
}
