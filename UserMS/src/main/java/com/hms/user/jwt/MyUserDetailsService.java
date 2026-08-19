package com.hms.user.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hms.user.dto.UserDto;
import com.hms.user.exception.HmsException;
import com.hms.user.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		try {
			UserDto userDto = userService.getUser(email);
			return new CustomUserDetails(userDto.getId(), userDto.getEmail(),userDto.getEmail(),
					userDto.getPassword(),userDto.getRole(),userDto.getName(),userDto.getProfileId(),null);
		} catch (HmsException e) {
			 throw new UsernameNotFoundException("User not found");
		}
		
	}

}
