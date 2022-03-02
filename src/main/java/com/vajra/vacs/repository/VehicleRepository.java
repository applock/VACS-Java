package com.vajra.vacs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vajra.vacs.pojo.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
 
}
