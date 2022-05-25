package com.vajra.vacs.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime sameDayAfter5Year = today.plusYears(1);
		String now = today.format(DateTimeFormatter.ISO_DATE_TIME);
		String fiveYears = sameDayAfter5Year.format(DateTimeFormatter.ISO_DATE_TIME);

		for (int i = 0; i < anprs.length; i++) {
			AnprRecord ar = new AnprRecord();
			ar.setAnprIp(anprs[i]);
			ar.setVehicleNo(vechileNo);

			try {
				UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(anprs[i])
						.path(ANPR_URL).queryParam("action", "insert").queryParam("name", "TrafficRedList")
						.queryParam("PlateNumber", vechileNo).queryParam("MasterOfCar", vechileNo)
						.queryParam("PlateColor", "Black").queryParam("VehicleColor", "Black")
						.queryParam("BeginTime", now).queryParam("CancelTime", fiveYears)
						.queryParam("AuthorityList.OpenGate", "true").build();

				logger.debug("addVehicle :: calling anpr add vehicle url - {}", uriComponents.toUriString());

				String regNoRaw = callApnrUsingDigest(uriComponents.toUriString());
				logger.debug("addVehicle : Result received - {}", regNoRaw);

				String regNo = regNoRaw.split("=")[1];
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

					String delResp = callApnrUsingDigest(uriComponents.toUriString());
					logger.debug("deleteVehicle : Result received - {}", delResp);
				} catch (Exception e) {
					logger.error("deleteVehicle : exception - {}", e);
				}
			}

		}
		anprRepo.deleteVehicleByNo(vechileNo);
	}

	protected String callApnrUsingDigest(String target) throws IOException {
		logger.debug("callApnrUsingDigest : target - {}", target);
		URL url = new URL(target);
		HttpHost targetHost = new HttpHost(url.getHost(), -1, null);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(anprUser, anprPassword));
		AuthCache authCache = new BasicAuthCache();
		DigestScheme digestScheme = new DigestScheme();
		digestScheme.overrideParamter("realm", "some-realm");
		digestScheme.overrideParamter("nonce", "whatever");
		authCache.put(targetHost, digestScheme);

		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);

		HttpPost httpPost = new HttpPost(target);

		CloseableHttpResponse response = httpClient.execute(targetHost, httpPost, context);
		logger.debug("callApnrUsingDigest : Response - " + response);
		org.apache.http.HttpEntity entity = response.getEntity();

		try (InputStream fis = entity.getContent();
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr)) {

			String resp = br.readLine();
			// br.lines().forEach(line -> System.out.println(line));
			return resp;
		} finally {
			response.close();
		}
	}
}
