package com.hms.user.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.user.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserReposistory extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
