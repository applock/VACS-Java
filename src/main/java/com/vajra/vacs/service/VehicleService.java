package com.vajra.vacs.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vajra.vacs.pojo.Vehicle;
import com.vajra.vacs.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepo;

	public Optional<Vehicle> getVehicle(Integer id) {
		return vehicleRepo.findById(id);
	}

	public Vehicle saveVehicle(Vehicle v) {
		return vehicleRepo.save(v);
	}

	public Vehicle updateVehicle(Integer id, Vehicle v) {
		Optional<Vehicle> vo = vehicleRepo.findById(id);
		if (vo.isPresent()) {
			Vehicle veh = vo.get();
			veh.setActive(v.isActive());
			veh.setTemporaryLock(v.isTemporaryLock());
			veh.setOwnerName(v.getOwnerName());
			veh.setOwnerPhone(v.getOwnerPhone());
			veh.setOwnerEmail(v.getOwnerEmail());
			return vehicleRepo.save(veh);
		} else {
			throw new EntityNotFoundException("Vehicle with id: " + id + " not found.");
		}
	}

	public boolean deleteVehicle(Integer id) {
		Optional<Vehicle> vo = vehicleRepo.findById(id);
		if (vo.isPresent()) {
			Vehicle veh = vo.get();
			veh.setActive(false);
			vehicleRepo.save(veh);
			return true;
		} else {
			throw new EntityNotFoundException("Vehicle with id: " + id + " not found.");
		}
	}
}
