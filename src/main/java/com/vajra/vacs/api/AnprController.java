package com.vajra.vacs.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.NetSDK.INetSDK;
import com.company.NetSDK.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY;
import com.company.NetSDK.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY;
//import com.netsdk.demo.module.LoginModule;

@RestController
public class AnprController {

	private Logger logger = LoggerFactory.getLogger(AnprController.class);

	@GetMapping("/probe")
	ResponseEntity<HttpStatus> executeTask() {
		logger.debug("Probing started..");
		System.out.println("Probing started..");

		/*
		 * LoginModule lm = new LoginModule(); boolean loginStatus =
		 * lm.login("192.168.1.108", 37777, "admin", "V@jra01@");
		 * System.out.println("Login - " + loginStatus); logger.debug("Login - " +
		 * loginStatus);
		 */

		INetSDK.LoadLibrarys();
		
		INetSDK.Init(null);
		

		NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY in = new NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
		in.szUserName = new String("admin").getBytes();
		in.szPassword = new String("V@jra01@").getBytes();
		in.szIP = new String("192.168.1.108").getBytes();
		in.nPort = 37777;

		NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY out = new NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
		INetSDK.LoginWithHighLevelSecurity(in, out);
		System.out.println("Device Info - " + out.stuDeviceInfo);

		logger.debug("Probing done..");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
