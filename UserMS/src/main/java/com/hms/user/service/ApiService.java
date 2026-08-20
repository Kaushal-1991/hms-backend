package com.hms.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hms.user.dto.UserDto;
import com.hms.user.enums.Role;

import reactor.core.publisher.Mono;

@Service
public class ApiService {
	
	@Value("${profiles.url}")
	private String ProfileUrl;
	
	@Autowired
	private WebClient.Builder webClient;
	
	public Mono<Long> addProfile(UserDto userDto){
		if(userDto.getRole().equals(Role.DOCTOR)) {
			return webClient.build().post()
					                .uri(ProfileUrl+"/profile/doctor/add")
					                .bodyValue(userDto)
					                .retrieve()
					                .bodyToMono(Long.class);
		}else if(userDto.getRole().equals(Role.PATIENT)) {
			return webClient.build().post()
					                .uri(ProfileUrl+"/profile/patient/add")
					                .bodyValue(userDto)
					                .retrieve()
					                .bodyToMono(Long.class);
		}
		
		return null;
	}
	
}
