package com.hms.profile.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.profile.dto.PatientDto;
import com.hms.profile.entity.Patient;
import com.hms.profile.exceptions.HmsException;
import com.hms.profile.repository.PatientRepository;
import com.hms.profile.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public Long addPatient(PatientDto patientDto) throws HmsException {
		if(patientDto.getEmail() != null && patientRepository.findByEmail(patientDto.getEmail()).isPresent()) {
			throw new HmsException("PATIENT_EMAIL_ALREADY_EXISTS");
		}
		
		if(patientDto.getAadharNo() !=null && patientRepository.findByAadharNo(patientDto.getAadharNo()).isPresent()) {
			throw new HmsException("PATIENT_AADHAR_ALREADY_EXISTS");
		}
		return patientRepository.save(patientDto.toEntity()).getId();
	}

	@Override
	public PatientDto getPatientById(Long id) throws HmsException {
		return patientRepository.findById(id).orElseThrow(() -> new HmsException("PATIENT_NOT_FOUND")).toDto();
	}

	@Override
	public PatientDto updatePatient(PatientDto patientDto) throws HmsException {
		patientRepository.findById(patientDto.getId()).orElseThrow(() -> new HmsException("PATIENT_NOT_FOUND"));
		Patient savedEntity = patientRepository.save(patientDto.toEntity());
		return savedEntity.toDto();
	}

	@Override
	public Boolean isPatientExixts(Long id) throws HmsException {
		return patientRepository.existsById(id);
	}

}
