package com.hms.user.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hms.user.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	private String password;
	private Role role;
	private String name;
	private Long profileId;
	private Collection<? extends GrantedAuthority> authorities;
	
}
