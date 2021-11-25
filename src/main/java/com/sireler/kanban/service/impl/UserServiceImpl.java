package com.sireler.kanban.service.impl;

import com.sireler.kanban.exception.KanbanApiException;
import com.sireler.kanban.model.Role;
import com.sireler.kanban.model.User;
import com.sireler.kanban.repository.RoleRepository;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new KanbanApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new KanbanApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();

        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);

        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id)
                .orElse(null);

        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
