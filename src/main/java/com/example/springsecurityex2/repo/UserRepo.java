package com.example.springsecurityex2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springsecurityex2.Entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
