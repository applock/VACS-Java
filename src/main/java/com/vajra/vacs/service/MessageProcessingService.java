package com.vajra.vacs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vajra.vacs.pojo.MqttInputMessage;
import com.vajra.vacs.repository.VehicleRepository;

@Service
public class MessageProcessingService {
	private Logger logger = LoggerFactory.getLogger(MessageProcessingService.class);

	@Autowired
	private VehicleRepository vehicleRepo;

	@Async
	@Transactional
	public void processMqttMessage(String payload) {
		logger.debug("processMqttMessage : payload - {}", payload);

		ObjectMapper mapper = new ObjectMapper();
		try {
			MqttInputMessage inputMsg = mapper.readValue(payload, MqttInputMessage.class);

			switch (inputMsg.getEventType()) {
			case "Change_ResidentProfileStatus":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Change_ResidentProfileStatus\",\"residentId\":2031, \"residentProfleStatusId\":1} -t messageToVacs
				logger.debug("processMqttMessage : Event Type # Change_ResidentProfileStatus | resident id - {}, profile status id - {}",
						inputMsg.getResidentId(), inputMsg.getResidentProfleStatusId());
				vehicleRepo.updateResidentProfileStatus(inputMsg.getResidentId(), inputMsg.getResidentProfleStatusId());
				break;
			case "Delete_ResidentProfile":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Delete_ResidentProfile\",\"residentId\":27} -t messageToVacs
				logger.debug("processMqttMessage : Event Type # Delete_ResidentProfile | resident id - {}",
						inputMsg.getResidentId());
				vehicleRepo.deleteResidentProfileById(inputMsg.getResidentId());
				break;
			case "Change_VechileStatus":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Change_VechileStatus\", \"vehicleId\":32} -t messageToVacs */
				logger.debug("processMqttMessage : Event Type # Change_VechileStatus | vehicle id - {}",
						inputMsg.getVehicleId());
				vehicleRepo.toggleVehicleStatus(inputMsg.getVehicleId());
				break;
			case "Change_ResidentProfileType":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Change_ResidentProfileType\",\"residentId\":2031, \"profileTypeId\":2} -t messageToVacs
				logger.debug("processMqttMessage : Event Type # Change_ResidentProfileType | resident id - {}, profile type id - {}",
						inputMsg.getResidentId(), inputMsg.getProfileTypeId());
				vehicleRepo.updateResidentProfileType(inputMsg.getResidentId(), inputMsg.getProfileTypeId());
				break;
			case "Delete_Vehicle":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Delete_Vehicle\",\"vehicleId\":27} -t messageToVacs
				logger.debug("processMqttMessage : Event Type # Delete_Vehicle | vehicle id - {}",
						inputMsg.getVehicleId());
				vehicleRepo.deleteVehicleById(inputMsg.getVehicleId());
				break;
			case "Change_SoftLock":
				// mosquitto_pub -h localhost -m {\"eventType\":\"Change_SoftLock\",\"vehicleId\":27,\"vehicleSoftLock\":false} -t messageToVacs
				logger.debug("processMqttMessage : Event Type # Change_SoftLock | vehicle id - {}, soft lock - {}",
						inputMsg.getVehicleId(), inputMsg.getVehicleSoftLock());
				vehicleRepo.updateSoftLock(inputMsg.getVehicleSoftLock(), inputMsg.getVehicleId());
				break;
			default:
				logger.error("processMqttMessage : mqtt json event type is invalid - {}", inputMsg.getEventType());
				break;

			}
		} catch (JsonProcessingException e) {
			logger.error("processMqttMessage : mqtt json payload processing exception - {}", e.getMessage());
		}
	}
}
