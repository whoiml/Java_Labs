package com.labs.ad_board.mapper;

import com.labs.ad_board.db.entity.User;
import com.labs.ad_board.dto.UserCreateEditDto;
import com.labs.ad_board.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User map(UserCreateEditDto user) {
        return User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();
    }

    public UserReadDto map(User user) {
        return UserReadDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

}
