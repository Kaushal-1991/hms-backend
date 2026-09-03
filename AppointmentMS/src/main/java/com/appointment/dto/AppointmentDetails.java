package com.appointment.dto;

import java.time.LocalDateTime;

import com.appointment.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetails {
	private Long id;
	private Long patientId;
	private String patientName;
	private String patientPhone;
	private Long doctorId;
	private String doctorName;
	private Status status;
	private String reason;
	private String notes;
	private LocalDateTime appointmentTime;
	
	public AppointmentDetails(
	        Long id,
	        Long patientId,
	        String patientName,
	        String patientPhone,
	        Long doctorId,
	        String doctorName,
	        LocalDateTime appointmentTime,
	        Status status,
	        String reason,
	        String notes) {

	    this.id = id;
	    this.patientId = patientId;
	    this.patientName = patientName;
	    this.patientPhone = patientPhone;
	    this.doctorId = doctorId;
	    this.doctorName = doctorName;
	    this.appointmentTime = appointmentTime;
	    this.status = status;
	    this.reason = reason;
	    this.notes = notes;
	}

}
