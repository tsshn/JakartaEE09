package com.yatsyshyn.JakartaEE09.repositories;

import com.yatsyshyn.JakartaEE09.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT user FROM User user LEFT JOIN FETCH user.permissions LEFT JOIN FETCH user.liked WHERE user.login = :login")
    Optional<User> get(@Param("login") String login);
}
