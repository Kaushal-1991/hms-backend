package com.hms.profile.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.profile.dto.DoctorDropdowns;
import com.hms.profile.dto.DoctorDto;
import com.hms.profile.exceptions.HmsException;
import com.hms.profile.repository.DoctorRepository;
import com.hms.profile.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public Long addDoctor(DoctorDto doctorDto) throws HmsException {
		
	    if (doctorDto.getEmail() != null && doctorRepository.findByEmail(doctorDto.getEmail()).isPresent()) {
	        throw new HmsException("DOCTOR_EMAIL_ALREADY_EXISTS");
	    }
	    if (doctorDto.getLicenceNo() != null && doctorRepository.findByLicenceNo(doctorDto.getLicenceNo()).isPresent()) {
	        throw new HmsException("DOCTOR_LICENCE_NO_ALREADY_EXISTS");
	    }

	   return doctorRepository.save(doctorDto.toEntity()).getId();
	}

	@Override
	public DoctorDto getDoctorById(Long id) throws HmsException {
		return doctorRepository.findById(id).orElseThrow(() -> new HmsException("DOCTOR_NOT_FOUND")).toDto();
	}

	@Override
	public DoctorDto updateDoctor(DoctorDto doctorDto) throws HmsException {
		doctorRepository.findById(doctorDto.getId()).orElseThrow(() -> new HmsException("DOCTOR_NOT_FOUND")).toDto();
		return doctorRepository.save(doctorDto.toEntity()).toDto();
	}

	@Override
	public Boolean isDoctorExists(Long id) throws HmsException {
		return doctorRepository.existsById(id);
	}

	@Override
	public List<DoctorDropdowns> getDoctorDropDowns() throws HmsException{
		// TODO Auto-generated method stub
		return doctorRepository.findAllDoctorDropdowns();
	}

}
