package com.timesheet.controller;

import com.timesheet.dto.UserAuthDto;
import com.timesheet.services.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_USER')")
    public void registerUser(@RequestBody UserAuthDto userAuthDto) {
        userService.registerUser(userAuthDto);
    }

}
