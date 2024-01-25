package com.mohirdev.mohirdev.repository;

import com.mohirdev.mohirdev.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
