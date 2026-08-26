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
}
