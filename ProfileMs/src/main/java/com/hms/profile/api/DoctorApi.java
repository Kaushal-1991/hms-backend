package com.hms.profile.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.profile.dto.DoctorDto;
import com.hms.profile.exceptions.HmsException;
import com.hms.profile.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile/doctor")
@Validated
@CrossOrigin
public class DoctorApi {
	@Autowired
	private DoctorService doctorService;
	
	@PostMapping("/add")
	public ResponseEntity<Long> addDoctor(@RequestBody @Valid DoctorDto doctorDto) throws HmsException{
		Long addDoctor = doctorService.addDoctor(doctorDto);
		return new ResponseEntity<>(addDoctor,HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<DoctorDto> getDoctor(@PathVariable Long id) throws HmsException{
		DoctorDto doctor = doctorService.getDoctorById(id);
		return new ResponseEntity<>(doctor,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<DoctorDto> updateDto(@RequestBody DoctorDto doctorDto) throws HmsException{
		return new ResponseEntity<>(doctorService.updateDoctor(doctorDto),HttpStatus.OK);
	}
	
	@GetMapping("/isExists/{id}")
	public ResponseEntity<Boolean> isDoctorExists(@PathVariable Long id) throws HmsException{
		return new ResponseEntity<>(doctorService.isDoctorExists(id),HttpStatus.OK);
	}
}
