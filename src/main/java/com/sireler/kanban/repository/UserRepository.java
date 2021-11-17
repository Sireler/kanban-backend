package com.sireler.kanban.repository;

import com.sireler.kanban.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
