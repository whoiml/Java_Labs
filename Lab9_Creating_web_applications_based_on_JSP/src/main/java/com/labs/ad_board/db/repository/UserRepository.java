package com.labs.ad_board.db.repository;

import com.labs.ad_board.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select COUNT(u) from User u where upper(u.username) = upper(:username)")
    int countUserByUsername(String username);

    @Query("select COUNT(u) from User u where upper(u.email) = upper(:email)")
    int countUserByEmail(String email);

    @Query("select u from User u where UPPER(u.username) = UPPER(:username)")
    Optional<User> loadUserByUsername(String username);

    @Query("select u.username from User u where u.id = :id")
    Optional<String> getUsernameByUserId(int id);
}
