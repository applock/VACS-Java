package com.vajra.vacs.api;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vajra.vacs.pojo.Response;
import com.vajra.vacs.pojo.VajraTransaction;
import com.vajra.vacs.pojo.Vehicle;
import com.vajra.vacs.pojo.VehicleLogs;
import com.vajra.vacs.pojo.VehicleMinimized;
import com.vajra.vacs.service.VehicleService;

@RestController
public class VehicleController {
	private Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	private VehicleService vehicleService;

	@PostMapping("/vehicle")
	ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle veh) {
		logger.debug("addVehicle :: Received request to add vehicle: {}", veh);
		Vehicle v = vehicleService.saveVehicle(veh);
		return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
	}

	@GetMapping("/vehicle/{id}")
	ResponseEntity<Vehicle> getVehicle(@PathVariable("id") Integer id) {
		logger.debug("getVehicle :: Received request to get vehicle with id: {}", id);
		Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
		if (vehicle.isPresent()) {
			Vehicle v = vehicle.get();
			if (v.isActive())
				return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/vehicleByNo/{vehNo}")
	ResponseEntity<Vehicle> getVehicleByName(@PathVariable("vehNo") String vehNo) {
		logger.debug("getVehicleByName :: Received request to get vehicle with vehNo: {}", vehNo);
		Optional<Vehicle> vehicle = vehicleService.getVehicleByVehicleNo(vehNo);
		if (vehicle.isPresent()) {
			Vehicle v = vehicle.get();
			if (v.isActive())
				return new ResponseEntity<Vehicle>(v, HttpStatus.OK);
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/vehicleMinByNo/{vehNo}")
	ResponseEntity<VehicleMinimized> getVehicleByNameMin(@PathVariable("vehNo") String vehNo) {
		logger.debug("getVehicleByNameMin :: Received request to get vehicle with vehNo: {}", vehNo);
		Optional<Vehicle> vehicle = vehicleService.getVehicleByVehicleNo(vehNo);
		if (vehicle.isPresent()) {
			Vehicle v = vehicle.get();
			if (v.isActive())
				return new ResponseEntity<VehicleMinimized>(VehicleMinimized.builder()
						.withProfileTypeId(v.getProfileTypeId()).withRecidentName(v.getRecidentName())
						.withRecidentProfileStatusId(v.getRecidentProfileStatusId()).withUnitName(v.getUnitName())
						.withVehicleNo(v.getVehicleNo()).withVehicleSoftLock(v.isActive() ? "Active" : "Locked")
						.withVehicleStatusId(v.getVehicleStatusId()).build(), HttpStatus.OK);
			return new ResponseEntity<VehicleMinimized>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<VehicleMinimized>(HttpStatus.NOT_FOUND);
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

	@GetMapping("/pullFrom/vajraApp")
	ResponseEntity<HttpStatus> pullFromVajraApp() {
		logger.debug("pullFromVajraApp :: Received request ..");

		vehicleService.pullFromVajraApp(null);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@PostMapping("/transaction")
	ResponseEntity<Response> vehicleTrafficLog(@RequestBody VehicleLogs vlog) {
		logger.debug("vehicleTrafficLog :: Received transaction {}", vlog);

		vlog.setIsSync(false);
		VehicleLogs vl = vehicleService.logVechile(vlog);
		logger.debug("vehicleTrafficLog :: Vehicle transaction logged. Pushing to Vajra App..");

		String snapBase64 = null;
		if (StringUtils.hasText(vlog.getSnapURL())) {
			try {
				logger.debug("vehicleTrafficLog :: snap file url - {}", vlog.getSnapURL());
				byte[] fileContent = FileUtils.readFileToByteArray(new File(vlog.getSnapURL()));
				snapBase64 = Base64.getEncoder().encodeToString(fileContent);
			} catch (IOException e) {
				logger.debug("vehicleTrafficLog :: Snap URL to Base64 failed due to {}", e.getMessage());
				return new ResponseEntity<Response>(Response.builder().withCode("400").withMessage("error").build(),
						HttpStatus.BAD_REQUEST);
			}
		}

		vehicleService.pushVechileTransactionToVajra(VajraTransaction.builder().withIPAdderess(vl.getIPAdderess())
				.withIsSync(vl.getIsSync()).withPort(vl.getPort()).withResidentId(vl.getResidentId())
				.withSnapStringBase64(snapBase64).withTransactionDateTime(vl.getTransactionDateTime())
				.withTransactionType(vl.getTransactionType()).withVehicleId(vl.getVehicleId())
				.withVehicleNo(vl.getVehicleNo()).build(), vl);
		logger.debug("vehicleTrafficLog :: async vehicle transaction push to Vajra App initiated..");

		return new ResponseEntity<Response>(Response.builder().withCode("200").withMessage("success").build(),
				HttpStatus.OK);
	}
}
