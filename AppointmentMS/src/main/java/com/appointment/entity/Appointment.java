package com.appointment.entity;

import java.time.LocalDateTime;

import com.appointment.dto.AppointmentDto;
import com.appointment.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long patientId;
	private Long doctorId;
	private Status status;
	private String reason;
	private String notes;
	private LocalDateTime appointmentTime;
	
	public AppointmentDto toDto() {
		return new AppointmentDto(this.id,this.patientId,this.doctorId,this.status,this.reason,this.notes,this.appointmentTime);
	}
}
