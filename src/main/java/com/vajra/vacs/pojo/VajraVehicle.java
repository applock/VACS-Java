package com.vajra.vacs.pojo;

import java.io.Serializable;

public class VajraVehicle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5758151267156489459L;

	private int recidentId;
	private String recidentName;
	private int profileTypeId;
	private int unitId;
	private String unitName;
	private int vehicleId;
	private String vehicleNo;
	private String vehicleColor;
	private String vehicleModal;
	private String vehicleType;
	private int vehicleStatusId;
	private String vehicleStatusName;
	private boolean vehicleSoftLock;
	private String vehicleNPRImage;
	private int recidentProfileStatusId;

	public int getRecidentId() {
		return recidentId;
	}

	public void setRecidentId(int recidentId) {
		this.recidentId = recidentId;
	}

	public String getRecidentName() {
		return recidentName;
	}

	public void setRecidentName(String recidentName) {
		this.recidentName = recidentName;
	}

	public int getProfileTypeId() {
		return profileTypeId;
	}

	public void setProfileTypeId(int profileTypeId) {
		this.profileTypeId = profileTypeId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleModal() {
		return vehicleModal;
	}

	public void setVehicleModal(String vehicleModal) {
		this.vehicleModal = vehicleModal;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getVehicleStatusId() {
		return vehicleStatusId;
	}

	public void setVehicleStatusId(int vehicleStatusId) {
		this.vehicleStatusId = vehicleStatusId;
	}

	public String getVehicleStatusName() {
		return vehicleStatusName;
	}

	public void setVehicleStatusName(String vehicleStatusName) {
		this.vehicleStatusName = vehicleStatusName;
	}

	public boolean isVehicleSoftLock() {
		return vehicleSoftLock;
	}

	public void setVehicleSoftLock(boolean vehicleSoftLock) {
		this.vehicleSoftLock = vehicleSoftLock;
	}

	public String getVehicleNPRImage() {
		return vehicleNPRImage;
	}

	public void setVehicleNPRImage(String vehicleNPRImage) {
		this.vehicleNPRImage = vehicleNPRImage;
	}

	public int getRecidentProfileStatusId() {
		return recidentProfileStatusId;
	}

	public void setRecidentProfileStatusId(int recidentProfileStatusId) {
		this.recidentProfileStatusId = recidentProfileStatusId;
	}

	@Override
	public String toString() {
		return "VajraVehicle [recidentId=" + recidentId + ", recidentName=" + recidentName + ", profileTypeId="
				+ profileTypeId + ", unitId=" + unitId + ", unitName=" + unitName + ", vehicleId=" + vehicleId
				+ ", vehicleNo=" + vehicleNo + ", vehicleColor=" + vehicleColor + ", vehicleModal=" + vehicleModal
				+ ", vehicleType=" + vehicleType + ", vehicleStatusId=" + vehicleStatusId + ", vehicleStatusName="
				+ vehicleStatusName + ", vehicleSoftLock=" + vehicleSoftLock + ", vehicleNPRImage=" + vehicleNPRImage
				+ ", recidentProfileStatusId=" + recidentProfileStatusId + "]";
	}

}