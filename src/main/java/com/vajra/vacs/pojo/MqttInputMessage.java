package com.vajra.vacs.pojo;

import java.io.Serializable;

public class MqttInputMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7728307733767183057L;

	private String eventType;
	private Integer residentId;
	private Integer residentProfleStatusId;
	private Integer profileTypeId;
	private Integer vehicleId;
	private Boolean vehicleSoftLock;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Integer getResidentId() {
		return residentId;
	}

	public void setResidentId(Integer residentId) {
		this.residentId = residentId;
	}

	public Integer getResidentProfleStatusId() {
		return residentProfleStatusId;
	}

	public void setResidentProfleStatusId(Integer residentProfleStatusId) {
		this.residentProfleStatusId = residentProfleStatusId;
	}

	public Integer getProfileTypeId() {
		return profileTypeId;
	}

	public void setProfileTypeId(Integer profileTypeId) {
		this.profileTypeId = profileTypeId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Boolean getVehicleSoftLock() {
		return vehicleSoftLock;
	}

	public void setVehicleSoftLock(Boolean vehicleSoftLock) {
		this.vehicleSoftLock = vehicleSoftLock;
	}

}
