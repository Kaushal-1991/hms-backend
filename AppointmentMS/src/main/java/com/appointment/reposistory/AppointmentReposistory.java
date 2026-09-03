package com.appointment.reposistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.dto.AppointmentDetails;
import com.appointment.entity.Appointment;

public interface AppointmentReposistory extends JpaRepository<Appointment, Long> {
	@Query("""
			    SELECT new com.appointment.dto.AppointmentDetails(
			        a.id,
			        a.patientId,
			        null,
			        null,
			        a.doctorId,
			        null,
			        a.appointmentTime,
			        a.status,
			        a.reason,
			        a.notes
			    )
			    FROM Appointment a
			    WHERE a.patientId = :patientId
			""")
	List<AppointmentDetails> findAllByPatientId(@Param("patientId") Long patientId);
}
