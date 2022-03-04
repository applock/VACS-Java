package com.vajra.vacs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vajra.vacs.pojo.VehicleLogs;

public interface VehicleLogsRepository extends JpaRepository<VehicleLogs, Integer> {

	@Query("SELECT vl FROM VehicleLogs vl where vl.IsSync=false")
	public List<VehicleLogs> findUnsyncedLogs();

}
