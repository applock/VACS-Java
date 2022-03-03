package com.vajra.vacs.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class VajraVehicleResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2630916609955583426L;

	@Override
	public String toString() {
		return "VajraVehicleResponse [code=" + code + ", message=" + message + ", list=" + list + "]";
	}

	private String code;
	private String message;
	private ArrayList<VajraVehicle> list;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<VajraVehicle> getList() {
		return list;
	}

	public void setList(ArrayList<VajraVehicle> list) {
		this.list = list;
	}

}