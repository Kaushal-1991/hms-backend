package com.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;

import reactor.core.publisher.Mono;

@Service
public class ApiService {
	
	@Autowired
	private WebClient.Builder webClient;
	
	public Mono<Boolean> isDocotorExists(Long id){
		return webClient.build().get()
                .uri("http://localhost:8081/profile/doctor/isExists/"+id)
                .retrieve()
                .bodyToMono(Boolean.class);
	}
	
	public Mono<Boolean> isPatientExists(Long id){
		return webClient.build().get()
                .uri("http://localhost:8081/profile/patient/isExists/"+id)
                .retrieve()
                .bodyToMono(Boolean.class);
	}
	
	public Mono<DoctorDto> getDoctorDetails(Long id){
		return webClient.build().get()
				        .uri("http://localhost:8081/profile/doctor/get/"+id)
				        .retrieve()
				        .bodyToMono(DoctorDto.class);
	}
	
	public Mono<PatientDto> getPatientDetails(Long id){
		return webClient.build().get()
				        .uri("http://localhost:8081/profile/patient/get/"+id)
				        .retrieve()
				        .bodyToMono(PatientDto.class);
	}
	
	
}
