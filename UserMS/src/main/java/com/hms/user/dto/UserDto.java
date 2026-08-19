package com.hms.user.dto;

import com.hms.user.entity.User;
import com.hms.user.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Please enter a valid email")
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must contain at least one uppercase, one lowercase, one digit, one special character and must be 8-15 characters long")
	private String password;
	private Role role;
	private Long profileId;

	public User toEntity() {
		return new User(this.id, this.name, this.email, this.password, this.role,this.profileId);
	}
}
