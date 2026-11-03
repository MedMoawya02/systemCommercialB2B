package com.microtech.b2b.controller;

import com.microtech.b2b.dto.request.UserRequest;
import com.microtech.b2b.dto.response.UserResponse;
import com.microtech.b2b.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> creat(@Valid @RequestBody UserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @PutMapping("/{id}")
    public UserResponse update(@Valid @PathVariable Long id,@RequestBody UserRequest request){
        return userService.update(id,request);
    }

    @GetMapping("/{id}")
    public UserResponse findUserById(@PathVariable Long id){
        return userService.findById(id);
    }
    @GetMapping
    public List<UserResponse> findAll(){
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delte(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
