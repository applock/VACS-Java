package com.vajra.vacs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vajra.vacs.pojo.AnprRecord;

public interface AnprRepository extends JpaRepository<AnprRecord, Integer> {

	@Modifying
	@Query("DELETE AnprRecord ar where ar.vehicleNo=?1")
	public int deleteVehicleByNo(String vehicleNo);

	@Query("SELECT ar FROM AnprRecord ar where ar.vehicleNo=?1")
	public Optional<List<AnprRecord>> findVehicleByNo(String vehicleNo);
}
