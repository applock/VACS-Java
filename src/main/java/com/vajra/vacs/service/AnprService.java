package com.vajra.vacs.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.vajra.vacs.pojo.AnprRecord;
import com.vajra.vacs.repository.AnprRepository;

@Service
public class AnprService {

	private Logger logger = LoggerFactory.getLogger(AnprService.class);

	@Autowired
	@Qualifier("restAnprTemplate")
	RestTemplate restTemplate;

	@Value("${anpr.ip}")
	private String[] anprs;

	@Value("${anpr.user}")
	private String anprUser;

	@Value("${anpr.password}")
	private String anprPassword;

	@Autowired
	private AnprRepository anprRepo;

	final String ANPR_URL = "/cgi-bin/recordUpdater.cgi";

	@Async
	@Transactional
	public void addVehicle(String vechileNo) {
		logger.debug("addVehicle : Starting for vechileNo - {}", vechileNo);

		for (int i = 0; i < anprs.length; i++) {
			AnprRecord ar = new AnprRecord();
			ar.setAnprIp(anprs[i]);
			ar.setVehicleNo(vechileNo);

			try {
				UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(anprs[i])
						.path(ANPR_URL).queryParam("action", "insert").queryParam("name", "TrafficRedList")
						.queryParam("PlateNumber", vechileNo).queryParam("MasterOfCar", vechileNo)
						.queryParam("PlateColor", "Black").queryParam("VehicleColor", "Black")
						.queryParam("BeginTime", "Black").queryParam("CancelTime", "Black")
						.queryParam("AuthorityList.OpenGate", "true").build();

				logger.debug("addVehicle :: calling anpr add vehicle url - {}", uriComponents.toUriString());

				HttpHeaders headers = new HttpHeaders();
				String authStr = anprUser + ":" + anprPassword;
				// headers.add("Authorization", "Basic " +
				// Base64.getEncoder().encodeToString(authStr.getBytes()));

				HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

				ResponseEntity<String> result = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST,
						requestEntity, String.class);
				logger.debug("addVehicle : Result received - {}", result);

				String regNo = result.getBody().split("=")[1];
				logger.debug("addVehicle : vechileNo - {} at ip - {} got regNo - {}", anprs[i], vechileNo, regNo);
				ar.setRegNo(regNo);
			} catch (Exception e) {
				logger.error("addVehicle : exception - {}", e);
			}
			anprRepo.save(ar);
		}

	}

	@Async
	@Transactional
	public void deleteVehicle(String vechileNo) {
		logger.debug("deleteVehicle : Starting for vechileNo - {}", vechileNo);

		Optional<List<AnprRecord>> arlo = anprRepo.findVehicleByNo(vechileNo);
		if (arlo.isPresent()) {
			List<AnprRecord> arl = arlo.get();
			for (int i = 0; i < arl.size(); i++) {
				AnprRecord ar = arl.get(i);
				try {
					UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(ar.getAnprIp())
							.path(ANPR_URL).queryParam("action", "remove").queryParam("name", "TrafficRedList")
							.queryParam("regno", ar.getRegNo()).build();

					logger.debug("deleteVehicle :: calling anpr delete vehicle url - {}", uriComponents.toUriString());

					HttpHeaders headers = new HttpHeaders();
					String authStr = anprUser + ":" + anprPassword;
					// headers.add("Authorization", "Basic " +
					// Base64.getEncoder().encodeToString(authStr.getBytes()));

					HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

					ResponseEntity<String> result = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST,
							requestEntity, String.class);
					logger.debug("deleteVehicle : Result received - {}", result);
				} catch (Exception e) {
					logger.error("deleteVehicle : exception - {}", e);
				}
			}

		}
		anprRepo.deleteVehicleByNo(vechileNo);
	}
}
