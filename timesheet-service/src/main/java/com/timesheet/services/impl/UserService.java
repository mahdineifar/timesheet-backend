package com.timesheet.services.impl;

import com.timesheet.RoleRepository;
import com.timesheet.dao.UserRepository;
import com.timesheet.dto.UserAuthDto;
import com.timesheet.entities.Role;
import com.timesheet.entities.User;
import com.timesheet.exceptions.RoleNotFoundException;
import com.timesheet.exceptions.UsernameAlreadyExistException;
import com.timesheet.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static com.timesheet.utils.Constants.*;

@Service
@Slf4j
public class UserService implements UserDetailsService, IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_WITH_USERNAME, username)));
    }

    @Override
    public void registerUser(UserAuthDto userAuthDto) {
        Optional<User> userOptional = userRepository.findByUsername(userAuthDto.getUsername());
        if (userOptional.isPresent()) {
            throw new UsernameAlreadyExistException(String.format(USER_WITH_USERNAME_EXISTS, userAuthDto.getUsername()));
        }
        User user = new User();
        user.setUsername(userAuthDto.getUsername().trim());
        user.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND, "ROLE_USER")));
        user.setRoles(Arrays.asList(role));
        userRepository.saveAndFlush(user);
        log.info(USER_CREATED_SUCCESSFULLY, userAuthDto.getUsername());
    }
}
