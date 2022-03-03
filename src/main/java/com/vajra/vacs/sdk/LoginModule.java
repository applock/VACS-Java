package com.vajra.vacs.sdk;

import java.io.File;

import com.netsdk.lib.NetSDKLib;
import com.netsdk.lib.ToolKits;

/**
 * * Implement login interface * Mainly are initialization, login and logout
 * functions.
 */
public class LoginModule {
	public static NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;
	public static NetSDKLib configsdk = NetSDKLib.CONFIG_INSTANCE;
// Login handle
	// public static LLong m_hLoginHandle = new LLong(0);
	private static boolean bInit = false;
	private static boolean bLogopen = false;

//Initialize 
	public static boolean init(NetSDKLib.fDisConnect disConnect, NetSDKLib.fHaveReConnect haveReConnect) {
		bInit = netsdk.CLIENT_Init(disConnect, null);
		if (!bInit) {
			System.out.println("Initialize SDK failed");
			return false;
		}
// (Optional) Open logs 
		NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();
		File path = new File("./sdklog/");
		if (!path.exists()) {
			path.mkdir();
		}
		String logPath = path.getAbsoluteFile().getParent() + "\\sdklog\\" + ToolKits.getDate() + ".log";
		setLog.nPrintStrategy = 0;
		setLog.bSetFilePath = 1;
		System.arraycopy(logPath.getBytes(), 0, setLog.szLogFilePath, 0, logPath.getBytes().length);
		System.out.println(logPath);
		setLog.bSetPrintStrategy = 1;
		bLogopen = netsdk.CLIENT_LogOpen(setLog);
		if (!bLogopen) {
			System.err.println("Failed to open NetSDK log");
		}
		// Set the callback of reconnection after disconnection. After setting, the SDK
		// will automatically reconnect when device disconnects.
		// This operation is optional but recommended.
		netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);
// (Optional) Set login timeout and login times 
		int waitTime = 5000; // Set the timeout of request response as 5 senconds
		int tryTimes = 1;
// Try to establish a link once during login
		netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

		// Set other network parameters, such as nWaittime of NET_PARAM,
		// member of nConnectTryNum and CLIENT_SetConnectTime. // (Optional) Set the
		// login timeout of device and login times having same
		// meaning.
		NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
		netParam.nConnectTime = 10000;
// Timeout of trying to establish a link during login
		netParam.nGetConnInfoTime = 3000; // Timeout of setting subconnection netsdk.CLIENT_SetNetworkParam(netParam);
		return true;
	}

// Clean up environment 
	public static void cleanup() {
		if (bLogopen) {
			netsdk.CLIENT_LogClose();
		}
		if (bInit) {
			netsdk.CLIENT_Cleanup();
		}
	}
}
