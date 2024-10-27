package com.spring.example.service;

import com.spring.example.exception.RoleNotFoundException;
import com.spring.example.exception.UserNotFoundException;
import com.spring.example.model.Role;
import com.spring.example.model.User;
import com.spring.example.model.dto.UserRequest;
import com.spring.example.repository.RoleRepository;
import com.spring.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.spring.example.enumeration.ErrorType.ROLE_NOT_FOUND;
import static com.spring.example.enumeration.ErrorType.USER_NOT_FOUND;
import static com.spring.example.enumeration.RoleType.USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }

    public User register(UserRequest userRequest) {
        final String encoded = passwordEncoder.encode(userRequest.getPassword());
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(encoded)
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .avatar(userRequest.getAvatar())
                .isEnabled(true)
                .roles(Collections.singletonList(getRole(USER.name())))
                .build();

        return saveUser(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND.getMessage(), username)));
        return user;
    }

    public boolean doesUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Role Methods
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND.getMessage(), roleName)));
        return role;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
