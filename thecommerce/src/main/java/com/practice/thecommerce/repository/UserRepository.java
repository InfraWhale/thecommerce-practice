package com.practice.thecommerce.repository;

import com.practice.thecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
