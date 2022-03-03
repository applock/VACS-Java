package com.vajra.vacs.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vajra.vacs.pojo.Vehicle;
import com.vajra.vacs.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepo;

	public Optional<Vehicle> getVehicleById(Integer id) {
		return vehicleRepo.findById(id);
	}
	
	public Optional<Vehicle> getVehicleByVehicleNo(String vehicleNo) {
		return vehicleRepo.findVehicleByVehicleNo(vehicleNo);
	}

	public Vehicle saveVehicle(Vehicle v) {
		return vehicleRepo.save(v);
	}

	public Vehicle updateVehicle(Integer id, Vehicle v) {
		Optional<Vehicle> vo = vehicleRepo.findById(id);
		if (vo.isPresent()) {
			Vehicle veh = vo.get();
			veh.setActive(v.isActive());
			if (StringUtils.hasText(v.getRecidentName()))
				veh.setRecidentName(v.getRecidentName());
			if (StringUtils.hasText(v.getVehicleColor()))
				veh.setVehicleColor(v.getVehicleColor());
			if (StringUtils.hasText(v.getVehicleModal()))
				veh.setVehicleModal(v.getVehicleModal());
			if (StringUtils.hasText(v.getVehicleType()))
				veh.setVehicleType(v.getVehicleType());
			if (StringUtils.hasText(v.getUnitName()))
				veh.setUnitName(v.getUnitName());
			if (StringUtils.hasText(v.getVehicleNPRImage()))
				veh.setVehicleNPRImage(v.getVehicleNPRImage());

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
