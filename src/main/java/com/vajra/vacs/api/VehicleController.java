package com.vajra.vacs.api;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vajra.vacs.pojo.Vehicle;
import com.vajra.vacs.service.VehicleService;

@RestController
public class VehicleController {
	private Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	private VehicleService vehicleService;

	@PostMapping("/vehicle")
	ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle veh) throws MqttException, InterruptedException {
		logger.debug("addVehicle :: Received request to add vehicle: {}", veh);
		Vehicle v = vehicleService.saveVehicle(veh);
		return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
	}

	@GetMapping("/vehicle/{id}")
	ResponseEntity<Vehicle> getVehicle(@PathVariable("id") Integer id) throws MqttException, InterruptedException {
		logger.debug("getVehicle :: Received request to get vehicle with id: {}", id);
		Optional<Vehicle> vehicle = vehicleService.getVehicle(id);
		if (vehicle.isPresent()) {
			Vehicle v = vehicle.get();
			if (v.isActive())
				return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/vehicle/{id}")
	ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") Integer id, @RequestBody Vehicle veh)
			throws MqttException, InterruptedException {
		logger.debug("updateVehicle :: Received request to update vehicle with id: {} and payload: {}", id, veh);
		Vehicle v = vehicleService.updateVehicle(id, veh);
		return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
	}

	@DeleteMapping("/vehicle/{id}")
	ResponseEntity<HttpStatus> deleteVehicle(@PathVariable("id") Integer id)
			throws MqttException, InterruptedException {
		logger.debug("deleteVehicle :: Received request to delete vehicle with id: {}", id);
		if (vehicleService.deleteVehicle(id))
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
	}
}
