package com.timesheet.controller;

import com.timesheet.dto.UserAuthDto;
import com.timesheet.services.IUserService;
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
    public void registerUser(@RequestBody UserAuthDto userAuthDto) {
        userService.registerUser(userAuthDto);
    }

}
