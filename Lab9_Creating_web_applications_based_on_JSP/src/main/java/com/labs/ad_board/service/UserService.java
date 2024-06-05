package com.labs.ad_board.service;

import com.labs.ad_board.db.entity.Role;
import com.labs.ad_board.db.entity.User;
import com.labs.ad_board.db.repository.UserRepository;
import com.labs.ad_board.dto.UserCreateEditDto;
import com.labs.ad_board.dto.UserReadDto;
import com.labs.ad_board.exception.EmailException;
import com.labs.ad_board.exception.UsernameException;
import com.labs.ad_board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public String getUsernameOfCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info("returning username of current user: " + username);
        return username;
    }

    public String getUsernameByUserId(int id) {
        return userRepository.getUsernameByUserId(id)
                .orElseThrow(() -> new UsernameException("No user with id " + id));
    }

    public int getUserIdByUsername(String username) {
        int uId = userRepository.loadUserByUsername(username)
                .orElseThrow(() -> new UsernameException(
                    "No user with username " + username
                ))
            .getId();
        log.info("Users id is " + uId);
        return uId;
    }

    @Transactional
    public UserReadDto registerNewUserAccount(UserCreateEditDto userCreateEditDto) {
        if (emailExist(userCreateEditDto.getEmail())) {
            throw new EmailException("Email already in use!");
        }
        if (usernameExist(userCreateEditDto.getUsername())) {
            throw new UsernameException("Username already in use");
        }

        //by default all users are USER
        //can be set to ADMIN through 'admin page'
        userCreateEditDto.setRole(Role.USER);

        return userMapper.map(
                userRepository.save(
                        userMapper.map(userCreateEditDto)
                )
        );
    }

    public boolean emailExist(String email) {
        return userRepository.countUserByEmail(email) == 1;
    }
    public boolean usernameExist(String username) {
        return userRepository.countUserByUsername(username) == 1;
    }

    public UserReadDto loadUserByUsername(String username) {
        return userRepository.loadUserByUsername(username)
            .map(userMapper::map)
            .orElseThrow(() -> new UsernameException("Username not found!"));
    }
}
