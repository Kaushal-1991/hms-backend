package com.appointment.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;

@FeignClient(name = "ProfileMs")
public interface ProfileClients {
   
	@GetMapping("/profile/doctor/isExists/{id}")
	public Boolean isDoctorExists(@PathVariable("id") Long id);
	
	@GetMapping("/profile/patient/isExists/{id}")
	public Boolean isPatientExists(@PathVariable("id") Long id);
	
	@GetMapping("/profile/doctor/get/{id}")
	public DoctorDto getDoctor(@PathVariable("id") Long id);
	
	@GetMapping("/profile/patient/get/{id}")
	public PatientDto getPatient(@PathVariable("id") Long id);
}
