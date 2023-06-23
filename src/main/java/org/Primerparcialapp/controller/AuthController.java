package org.Primerparcialapp.controller;

import org.Primerparcialapp.model.User;
import org.Primerparcialapp.service.UserService;
import org.Primerparcialapp.utils.ApiResponse;
import org.Primerparcialapp.utils.Constants;
import org.Primerparcialapp.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://127.0.0.1:5500/dashboard"})
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    private ApiResponse apiResponse;
    private final Map data = new HashMap();

    @Autowired
    private JWTUtil jwtUtil;

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody User user){
        try {
            data.put("token",userService.login(user));
            apiResponse = new ApiResponse(Constants.USER_LOGIN, data);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            apiResponse = new ApiResponse(e.getMessage(), "");
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
    }
}

