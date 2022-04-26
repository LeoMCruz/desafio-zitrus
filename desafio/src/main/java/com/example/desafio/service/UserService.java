package com.example.desafio.service;

import com.example.desafio.domain.model.User;
import com.example.desafio.domain.repository.UserRepository;
import com.example.desafio.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserDTO salvarUser(UserDTO userDTO){
        var user = User.builder()
                .senha(passwordEncoder.encode(userDTO.getSenha()))
                .login(userDTO.getLogin())
                .build();
        userRepository.save(user);
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .build();
    }

    public List<UserDTO> buscarTodos(Pageable pageable){
        return userRepository.findAll(pageable).stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .build())
                        .collect(Collectors.toList());
    }
    public UserDTO buscarById(Long id){
        return userRepository.findById(id).map(user -> UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .build()).orElse(null);
    }
}
