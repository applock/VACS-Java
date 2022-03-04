package com.vajra.vacs.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vajra.vacs.service.VehicleService;

@Component
public class VajraAppSyncScheduler {

	private Logger logger = LoggerFactory.getLogger(VajraAppSyncScheduler.class);

	@Value("${vajra.cronExp}")
	private String vajraCronExp;

	@Autowired
	private VehicleService vehicleService;

	@Scheduled(cron = "*/10 * * * *")
	public void cronJobSyncWithVajraAppSch() {
		logger.debug("cronJobSyncWithVajraAppSch : Starting..");
		vehicleService.checkUnSyncedAndPushToVajra();
		logger.debug("cronJobSyncWithVajraAppSch : Completed.");
	}
}