package com.vajra.vacs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vajra.vacs.pojo.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query("SELECT v FROM Vehicle v where v.vehicleNo=?1 and active=true")
	public Optional<Vehicle> findVehicleByVehicleNo(String vehicleNo);
	
	@Query("SELECT v FROM Vehicle v where v.vehicleId=?1 and active=true")
	public Optional<Vehicle> findVehicleByVehicleId(Integer vehicleId);

	@Modifying
	@Query("UPDATE Vehicle SET vehicleSoftLock=?1 where vehicleId=?2")
	public void updateSoftLock(Boolean vehicleSoftLock, Integer vehicleId);

	@Modifying
	@Query("DELETE Vehicle v where v.vehicleId=?1")
	public int deleteVehicleById(Integer vehicleId);

	@Modifying
	@Query("UPDATE Vehicle SET profileTypeId=?2 where recidentId=?1")
	public void updateResidentProfileType(Integer residentId, Integer profileTypeId);

	@Modifying
	@Query("UPDATE Vehicle v SET v.active = CASE v.active WHEN true THEN false ELSE true END where v.vehicleId=?1")
	public void toggleVehicleStatus(Integer vehicleId);

	@Modifying
	@Query("DELETE Vehicle v where v.recidentId=?1")
	public void deleteResidentProfileById(Integer residentId);

	@Modifying
	@Query("UPDATE Vehicle SET recidentProfileStatusId=?2 where recidentId=?1")
	public void updateResidentProfileStatus(Integer residentId, Integer residentProfleStatusId);
}
