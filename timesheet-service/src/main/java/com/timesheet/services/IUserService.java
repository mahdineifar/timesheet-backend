package com.timesheet.services;

import com.timesheet.dto.UserAuthDto;

public interface IUserService {
    void registerUser(UserAuthDto userAuthDto);
}
