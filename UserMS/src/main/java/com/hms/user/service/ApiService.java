package com.hms.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hms.user.dto.UserDto;
import com.hms.user.enums.Role;

import reactor.core.publisher.Mono;

@Service
public class ApiService {
	@Autowired
	private WebClient.Builder webClient;
	
	public Mono<Long> addProfile(UserDto userDto){
		if(userDto.getRole().equals(Role.DOCTOR)) {
			return webClient.build().post()
					                .uri("http://localhost:8081/profile/doctor/add")
					                .bodyValue(userDto)
					                .retrieve()
					                .bodyToMono(Long.class);
		}else if(userDto.getRole().equals(Role.PATIENT)) {
			return webClient.build().post()
					                .uri("http://localhost:8081/profile/patient/add")
					                .bodyValue(userDto)
					                .retrieve()
					                .bodyToMono(Long.class);
		}
		
		return null;
	}
	
}
