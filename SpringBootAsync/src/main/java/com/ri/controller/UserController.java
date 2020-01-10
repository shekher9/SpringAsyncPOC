package com.ri.controller;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ri.entity.UserEntity;
import com.ri.service.UserService;

@RestController
@Consumes(value = MediaType.MULTIPART_FORM_DATA_VALUE)
@Produces("application/json")
@RequestMapping("/async")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@PostMapping(value = "/users")
	public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            userservice.saveNewUser(file);
            }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users")
    public CompletableFuture<ResponseEntity> findAllUsers() {
       return  userservice.getAllUser().thenApply(ResponseEntity::ok);
    }


    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity getUsers(){
        CompletableFuture<List<UserEntity>> users1=userservice.getAllUser();
        CompletableFuture<List<UserEntity>> users2=userservice.getAllUser();
        CompletableFuture<List<UserEntity>> users3=userservice.getAllUser();
        CompletableFuture.allOf(users1,users2,users3).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}
