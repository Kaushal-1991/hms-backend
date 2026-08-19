package com.hms.profile.entity;

import java.time.LocalDate;

import com.hms.profile.dto.DoctorDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(unique = true)
	private String email;
	private LocalDate dob;
	private String phone;
	private String address;
	@Column(unique = true)
	private String licenceNo;
	private String specialization;
	private String department;
	private Integer totalExp;


	public DoctorDto toDto() {
		return new DoctorDto(this.id, this.name, this.email, this.dob, this.phone, this.address, this.licenceNo,
				this.specialization, this.department, this.totalExp);
	}
}
