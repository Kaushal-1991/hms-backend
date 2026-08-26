package com.appointment.serviceIml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.clients.ProfileClients;
import com.appointment.dto.AppointmentDetails;
import com.appointment.dto.AppointmentDto;
import com.appointment.dto.DoctorDto;
import com.appointment.dto.PatientDto;
import com.appointment.entity.Appointment;
import com.appointment.enums.Status;
import com.appointment.exception.HmsException;
import com.appointment.reposistory.AppointmentReposistory;
import com.appointment.service.ApiService;
import com.appointment.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentReposistory appointmentReposistory;

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private ProfileClients profileClients;

	@Override
	public Long scheduleAppointment(AppointmentDto appointmentDto) throws HmsException {
		return appointmentReposistory.save(appointmentDto.toEntity()).getId();
	}

	@Override
	public void cancelAppointment(Long appointmentId) throws HmsException {
		Appointment appointment = appointmentReposistory.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT-NOT-FOUND"));
		if (appointment.getStatus().equals(Status.CANCELLED)) {
			throw new HmsException("APPOINTMENT-ALREADY-CANCELLED");
		}
		appointment.setStatus(Status.CANCELLED);
		appointmentReposistory.save(appointment);
	}

	@Override
	public void completeAppointment(Long appointmentId) throws HmsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rescheduleAppointment(Long appointmentId, String newDateTime) throws HmsException {

	}

	@Override
	public AppointmentDto getAppointmentDetails(Long appointmentId) throws HmsException {
		return appointmentReposistory.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT-NOT-FOUND")).toDto();
	}

	@Override
	public AppointmentDetails getAppointmentwithName(Long appointmentId) throws HmsException {
		AppointmentDto appointmentDto = appointmentReposistory.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT-NOT-FOUND")).toDto();
		//DoctorDto doctor = apiService.getDoctorDetails(appointmentDto.getDoctorId()).block();
		//PatientDto patient = apiService.getPatientDetails(appointmentDto.getPatientId()).block();
		DoctorDto doctor = profileClients.getDoctor(appointmentDto.getDoctorId());
		PatientDto patient = profileClients.getPatient(appointmentDto.getPatientId());
		if (doctor == null || patient == null) {
			throw new HmsException("DOCTOR_OR_PATIENT_DETAILS_NOT_FOUND");
		}
		AppointmentDetails appointmentDetails = new AppointmentDetails(appointmentDto.getId(),
				appointmentDto.getPatientId(), patient.getName(), patient.getPhone(), appointmentDto.getDoctorId(),
				doctor.getName(), appointmentDto.getStatus(), appointmentDto.getReason(), appointmentDto.getNotes(),
				appointmentDto.getAppointmentTime());
		
		return appointmentDetails;
	}

}
