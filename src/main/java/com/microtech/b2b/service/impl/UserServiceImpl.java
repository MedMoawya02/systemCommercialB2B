package com.microtech.b2b.service.impl;

import com.microtech.b2b.dto.request.UserRequest;
import com.microtech.b2b.dto.response.UserResponse;
import com.microtech.b2b.entity.User;
import com.microtech.b2b.exception.BusinessException;
import com.microtech.b2b.exception.ResourceNotFoundException;
import com.microtech.b2b.mapper.UserMapper;
import com.microtech.b2b.repository.UserRepository;
import com.microtech.b2b.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse create(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("Ce email existe deja");
        }
        if (request.getPassword()==null||request.getPassword().isBlank()){
            throw new BusinessException("Le mot de passe est obligatoire");
        }
        User user=userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toRespone(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable : "+id));
        if(userRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new BusinessException("Ce email deja utilise");
        }
        userMapper.toUpdateEntity(request,user);
        if(request.getPassword()!=null&&!request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toRespone(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable"));
        return userMapper.toRespone(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Utilisateur introuvable"));
        userRepository.delete(user);
    }
}
