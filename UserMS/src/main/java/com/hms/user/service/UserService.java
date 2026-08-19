package com.hms.user.service;

import com.hms.user.dto.LoginDto;
import com.hms.user.dto.UserDto;
import com.hms.user.exception.HmsException;

public interface UserService {
   public void registerUser(UserDto userDto) throws HmsException;
   public String loginUser(LoginDto loginDto) throws HmsException ;
   public UserDto getUserById(Long id) throws HmsException ;
   public void updateUser(UserDto userDto);
   public UserDto getUser(String email) throws HmsException;
}
