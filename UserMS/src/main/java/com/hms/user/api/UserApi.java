package com.hms.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.user.dto.LoginDto;
import com.hms.user.dto.ResponseDto;
import com.hms.user.dto.UserDto;
import com.hms.user.exception.HmsException;
import com.hms.user.jwt.JwtUtils;
import com.hms.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserApi {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<ResponseDto> register(@RequestBody @Valid UserDto userDto) throws HmsException {
		userService.registerUser(userDto);
		return new ResponseEntity<>(new ResponseDto("Account Created"), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) throws HmsException {
		
		try {
			authenticationManager
			           .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		} catch (AuthenticationException e) {
			throw new HmsException("INVALID_CREDENTIAL");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
		final String jwt = jwtUtils.generateToken(userDetails);
		return new ResponseEntity<>(jwt,HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
