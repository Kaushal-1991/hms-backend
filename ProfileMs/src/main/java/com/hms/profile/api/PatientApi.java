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

import com.hms.profile.dto.PatientDto;
import com.hms.profile.exceptions.HmsException;
import com.hms.profile.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile/patient")
@Validated
@CrossOrigin
public class PatientApi {
 
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/add")
	public ResponseEntity<Long> addPatient(@RequestBody @Valid PatientDto patientDto) throws HmsException{
		Long addPatient = patientService.addPatient(patientDto);
		return new ResponseEntity<>(addPatient,HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<PatientDto> getPatient(@PathVariable Long id) throws HmsException{
		PatientDto patient = patientService.getPatientById(id);
		return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patientDto) throws HmsException{
		return new ResponseEntity<>(patientService.updatePatient(patientDto),HttpStatus.OK);
	}
	
	@GetMapping("/isExists/{id}")
	public ResponseEntity<Boolean> isPatientExists(@PathVariable Long id) throws HmsException{
		return new ResponseEntity<>(patientService.isPatientExixts(id),HttpStatus.OK);
	}
	
}
