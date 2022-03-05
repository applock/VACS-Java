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
import com.vajra.vacs.repository.VehicleLogsRepository;
import com.vajra.vacs.repository.VehicleRepository;

@Service
public class MessageProcessingService {
	private Logger logger = LoggerFactory.getLogger(MessageProcessingService.class);

	@Autowired
	private VehicleRepository vehicleRepo;

	@Autowired
	private VehicleLogsRepository logRepo;

	@Async
	@Transactional
	public void processMqttMessage(String payload) {
		logger.debug("processMqttMessage : payload - {}", payload);

		ObjectMapper mapper = new ObjectMapper();
		try {
			MqttInputMessage inputMsg = mapper.readValue(payload, MqttInputMessage.class);

			switch (inputMsg.getEventType()) {
			case "Change_ResidentProfileStatus":

				break;
			case "Delete_ResidentProfile":

				break;
			case "Change_VechileStatus":

				break;
			case "Change_ResidentProfileType":

				break;
			case "Delete_Vehicle":

				break;
			case "Change_SoftLock":
				logger.debug("processMqttMessage : Event Type # Change_SoftLock | {}, {}", inputMsg.getVehicleId(),
						inputMsg.getVehicleSoftLock());
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
