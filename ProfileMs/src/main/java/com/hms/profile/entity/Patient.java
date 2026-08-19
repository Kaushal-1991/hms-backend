package com.hms.profile.entity;

import java.time.LocalDate;

import com.hms.profile.dto.PatientDto;
import com.hms.profile.enums.BloodGroup;

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
public class Patient {

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
	private String aadharNo;
	private BloodGroup bloodGroup;
	private String allergies;
	private String chronicDiease;

	public PatientDto toDto() {
		return new PatientDto(this.id, this.name, this.email, this.dob, this.phone, this.address, this.aadharNo,
				this.bloodGroup,this.allergies,this.chronicDiease);
	}
}
