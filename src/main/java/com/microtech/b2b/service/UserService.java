package com.microtech.b2b.service;

import com.microtech.b2b.dto.request.UserRequest;
import com.microtech.b2b.dto.response.UserResponse;
import com.microtech.b2b.entity.User;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest user);
    UserResponse update(Long id, UserRequest request);
    UserResponse findById(Long id);
    List<User> finAll();
    void delete(Long id);
}
