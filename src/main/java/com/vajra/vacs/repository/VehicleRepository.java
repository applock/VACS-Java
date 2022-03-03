package com.vajra.vacs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vajra.vacs.pojo.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query("SELECT v FROM Vehicle v where v.vehicleNo=?1 and active=true")
	public Optional<Vehicle> findVehicleByVehicleNo(String vehicleNo);
}
