package com.appointment.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.entity.Appointment;

public interface AppointmentReposistory extends JpaRepository<Appointment, Long>{

}
