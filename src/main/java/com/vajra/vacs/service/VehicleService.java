package com.vajra.vacs.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.vajra.vacs.pojo.VajraTransaction;
import com.vajra.vacs.pojo.VajraVehicle;
import com.vajra.vacs.pojo.VajraVehicleResponse;
import com.vajra.vacs.pojo.Vehicle;
import com.vajra.vacs.pojo.VehicleLogs;
import com.vajra.vacs.repository.VehicleLogsRepository;
import com.vajra.vacs.repository.VehicleRepository;

@Service
public class VehicleService {

	private Logger logger = LoggerFactory.getLogger(VehicleService.class);

	@Autowired
	private VehicleRepository vehicleRepo;

	@Autowired
	private VehicleLogsRepository logRepo;

	@Autowired
	RestTemplate restTemplate;

	@Value("${vajra.appUrl}")
	String vajraAppVehicleUrl;

	@Value("${vajra.txnUrl}")
	String vajraAppTxnUrl;

	@Value("${vajra.snapFolder}")
	String snapFolder;

	@Value("${vajra.key}")
	String vajraAppkey;

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

	public void pullFromVajraApp() {
		logger.debug("pullFromVajraApp : Starting..");
		try {
			URI uri = new URI(vajraAppVehicleUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.set("ApiKeyKiosk", vajraAppkey);

			HttpEntity<VajraVehicleResponse> requestEntity = new HttpEntity<>(null, headers);

			ResponseEntity<VajraVehicleResponse> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
					VajraVehicleResponse.class);
			logger.debug("pullFromVajraApp : Result received.");

			VajraVehicleResponse vvr = result.getBody();
			if ("200".equals(vvr.getCode())) {
				ArrayList<VajraVehicle> vvl = vvr.getList();
				Iterator<VajraVehicle> vi = vvl.iterator();

				while (vi.hasNext()) {
					VajraVehicle vv = vi.next();

					Optional<Vehicle> existing = vehicleRepo.findVehicleByVehicleNo(vv.getVehicleNo());
					if (existing.isEmpty()) {
						Vehicle v = new Vehicle();
						v.setActive(vv.getVehicleStatusName().equals("Active"));
						v.setProfileTypeId(vv.getProfileTypeId());
						v.setRecidentId(vv.getRecidentId());
						v.setRecidentName(vv.getRecidentName());
						v.setRecidentProfileStatusId(vv.getRecidentProfileStatusId());
						v.setUnitId(vv.getUnitId());
						v.setUnitName(vv.getUnitName());
						v.setVehicleColor(vv.getVehicleColor());
						v.setVehicleId(vv.getVehicleId());
						v.setVehicleModal(vv.getVehicleModal());
						v.setVehicleNo(vv.getVehicleNo());
						v.setVehicleNPRImage(vv.getVehicleNPRImage());
						v.setVehicleSoftLock(vv.isVehicleSoftLock());
						v.setVehicleStatusId(vv.getVehicleStatusId());
						v.setVehicleType(vv.getVehicleType());

						vehicleRepo.save(v);
						logger.debug("pullFromVajraApp : Vehicle added {}", v);
					} else {
						logger.debug("pullFromVajraApp : Vehicle with number {} already exists", vv.getVehicleNo());
					}
				}
			} else {
				logger.error("pullFromVajraApp : Got a non-200 response code.");
			}
		} catch (URISyntaxException e) {
			logger.error("pullFromVajraApp : {}", e.getMessage());
		}

	}

	public VehicleLogs logVechile(VehicleLogs vlog) {
		return logRepo.save(vlog);
	}

	@Async
	public void pushVechileTransactionToVajra(VajraTransaction vajraTransaction, @NonNull VehicleLogs vl) {
		logger.debug("pushVechileTransactionToVajra : Starting for vehicle no - {}", vl.getVehicleNo());
		try {
			URI uri = new URI(vajraAppTxnUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.set("ApiKeyKiosk", vajraAppkey);

			HttpEntity<VajraTransaction> requestEntity = new HttpEntity<>(vajraTransaction, headers);

			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			logger.debug("pushVechileTransactionToVajra : Result received - {}", result);

			if (null != result && result.getBody() != null && result.getBody().contains("success")) {
				vl.setIsSync(true);
				logger.debug("pushVechileTransactionToVajra : Setting is sync to true");
				logRepo.save(vl);
			}

		} catch (Exception e) {
			logger.error("pushVechileTransactionToVajra : {}", e.getMessage());
		}
	}

	public void checkUnSyncedAndPushToVajra() {
		logger.debug("checkUnSyncedAndPushToVajra : Starting..");
		List<VehicleLogs> vll = logRepo.findUnsyncedLogs();
		logger.debug("checkUnSyncedAndPushToVajra : Count - {}", vll.size());

		Iterator<VehicleLogs> vlli = vll.iterator();
		while (vlli.hasNext()) {
			VehicleLogs vl = vlli.next();

			try {
				logger.debug("checkUnSyncedAndPushToVajra :: snap file url - {}", vl.getSnapURL());
				byte[] fileContent = FileUtils.readFileToByteArray(new File(vl.getSnapURL()));
				logger.debug("checkUnSyncedAndPushToVajra : Attempting Sync to Vajra App for vehicle no - {}",
						vl.getVehicleNo());

				pushVechileTransactionToVajra(VajraTransaction.builder().withIPAdderess(vl.getIPAdderess())
						.withIsSync(vl.getIsSync()).withPort(vl.getPort()).withResidentId(vl.getResidentId())
						.withSnapStringBase64(Base64.getEncoder().encodeToString(fileContent))
						.withTransactionDateTime(vl.getTransactionDateTime())
						.withTransactionType(vl.getTransactionType()).withVehicleId(vl.getVehicleId())
						.withVehicleNo(vl.getVehicleNo()).build(), vl);
			} catch (IOException e) {
				logger.error("checkUnSyncedAndPushToVajra : IOException - {}", e.getMessage());
			}

		}
	}
}
