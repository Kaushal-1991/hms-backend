package com.appointment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.clients.ProfileClients;
import com.appointment.dto.AppointmentDetails;
import com.appointment.dto.AppointmentDto;
import com.appointment.exception.HmsException;
import com.appointment.service.ApiService;
import com.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentApi {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private ProfileClients profileClients;

	@PostMapping("/schedule")
	public ResponseEntity<Long> scheduleAppointment(@RequestBody AppointmentDto appointmentDto) throws HmsException {
		//Boolean doctorExists = apiService.isDocotorExists(appointmentDto.getDoctorId()).block();
		Boolean doctorExists = profileClients.isDoctorExists(appointmentDto.getDoctorId());
		if (!doctorExists) {
			throw new HmsException("DOCTOR_NOT_FOUND");
		}
		//Boolean patientExists = apiService.isPatientExists(appointmentDto.getPatientId()).block();
		Boolean patientExists = profileClients.isPatientExists(appointmentDto.getPatientId());
		if (!patientExists) {
			throw new HmsException("PATIENT_NOT_FOUND");
		}
		return new ResponseEntity<>(appointmentService.scheduleAppointment(appointmentDto), HttpStatus.CREATED);
	}

	@PutMapping("/canceled/{appointmentId}")
	public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) throws HmsException {
		appointmentService.cancelAppointment(appointmentId);
		return new ResponseEntity<>("Appointment Cancelled Successfully !!!", HttpStatus.OK);
	}

	@GetMapping("/get/{appointmentId}")
	public ResponseEntity<AppointmentDto> getAppointmentDetails(@PathVariable Long appointmentId) throws HmsException {
		return new ResponseEntity<>(appointmentService.getAppointmentDetails(appointmentId), HttpStatus.OK);
	}
	
	@GetMapping("/get/details/{appointmentId}")
	public ResponseEntity<AppointmentDetails> getAppointmentDetailsWithName(@PathVariable Long appointmentId) throws HmsException {
		return new ResponseEntity<>(appointmentService.getAppointmentwithName(appointmentId), HttpStatus.OK);
	}
}
