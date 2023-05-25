package org.Primerparcialapp.controller;

import org.Primerparcialapp.Utils.ApiResponse;
import org.Primerparcialapp.Utils.Constants;
import org.Primerparcialapp.Utils.JWTUtil;
import org.Primerparcialapp.model.User;
import org.Primerparcialapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;

    @Autowired
    private JWTUtil jwtUtil;

    Map data = new HashMap<>();

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){
        try{
            data.put("token",userService.login(user));
            apiResponse = new ApiResponse(Constants.USER_LOGIN, data);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse(e.getMessage(), "");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
}

