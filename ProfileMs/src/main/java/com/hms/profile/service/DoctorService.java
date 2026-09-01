package com.hms.profile.service;

import java.util.List;

import com.hms.profile.dto.DoctorDropdowns;
import com.hms.profile.dto.DoctorDto;
import com.hms.profile.exceptions.HmsException;

public interface DoctorService {
    public Long addDoctor(DoctorDto doctorDto) throws HmsException;
    public DoctorDto getDoctorById(Long id) throws HmsException;
    public DoctorDto updateDoctor(DoctorDto doctorDto) throws HmsException;
    public Boolean isDoctorExists(Long id) throws HmsException;
    public List<DoctorDropdowns> getDoctorDropDowns() throws HmsException;
}
