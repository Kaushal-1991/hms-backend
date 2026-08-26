package com.appointment.dto;

import java.time.LocalDateTime;

import com.appointment.entity.Appointment;
import com.appointment.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
	private Long id;
	private Long patientId;
	private Long doctorId;
	private Status status;
	private String reason;
	private String notes;
	private LocalDateTime appointmentTime;
	
	public Appointment toEntity() {
		return new Appointment(this.id,this.patientId,this.doctorId,this.status,this.reason,this.notes,this.appointmentTime);
	}
}
