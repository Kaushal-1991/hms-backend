package com.hms.user.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.user.dto.LoginDto;
import com.hms.user.dto.UserDto;
import com.hms.user.entity.User;
import com.hms.user.exception.HmsException;
import com.hms.user.reposistory.UserReposistory;
import com.hms.user.service.ApiService;
import com.hms.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserReposistory userReposistory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApiService apiService;
	

	@Override
	public void registerUser(UserDto userDto) throws HmsException{
		Optional<User> opt = userReposistory.findByEmail(userDto.getEmail());
		if(opt.isPresent()) {
			throw new HmsException("USER_ALREADY_EXISTS");
		}
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Long profileId = apiService.addProfile(userDto).block();
		System.out.println("--------->"+profileId);
		userDto.setProfileId(profileId);
		userReposistory.save(userDto.toEntity());
	}

	@Override
	public String loginUser(LoginDto loginDto) throws HmsException {
		User user = userReposistory.findByEmail(loginDto.getEmail()).orElseThrow(() -> new HmsException("USER_NOT_FOUND"));
		if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			throw new HmsException("INVALID_CREDENTIAL");
		}
		user.setPassword(null);
		
		return null;	
	}

	@Override
	public UserDto getUserById(Long id) throws HmsException {
		return userReposistory.findById(id).orElseThrow(() -> new HmsException("USER_NOT_FOUND")).toDto();
	}

	@Override
	public void updateUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDto getUser(String email) throws HmsException {
		return userReposistory.findByEmail(email).orElseThrow(() -> new HmsException("USER_NOT_FOUND")).toDto();
	}

}
