package com.appointment.service;

import java.util.List;

import com.appointment.dto.AppointmentDetails;
import com.appointment.dto.AppointmentDto;
import com.appointment.exception.HmsException;

public interface AppointmentService {
    Long scheduleAppointment(AppointmentDto appointmentDto) throws HmsException;
    void cancelAppointment(Long appointmentId) throws HmsException;
    void completeAppointment(Long appointmentId) throws HmsException;
    void rescheduleAppointment(Long appointmentId,String newDateTime) throws HmsException;
    AppointmentDto getAppointmentDetails(Long appointmentId) throws HmsException;
    AppointmentDetails getAppointmentwithName(Long appointmentId) throws HmsException;
    List<AppointmentDetails> getAppointmentByPatientId(Long patientId);
}
