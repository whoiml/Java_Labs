package com.labs.ad_board.configuration;

import com.labs.ad_board.db.entity.User;
import com.labs.ad_board.db.repository.UserRepository;
import com.labs.ad_board.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Handling user login username = " + username);
        User user = userRepository.loadUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not exists by Username"
                ));

        Set<GrantedAuthority> authorities = Set.of(user.getRole());

        log.info("prepared UserDetails, sending...");
        log.info(user.toString());

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities
        );
    }
}
