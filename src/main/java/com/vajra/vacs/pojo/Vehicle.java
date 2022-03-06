package com.vajra.vacs.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Entity
@Table(name = "vehicle")
//@IdClass(VehicleIdClass.class)
public class Vehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3449126464232147353L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(accessMode = AccessMode.READ_ONLY)
	private Integer id;

	private String vehicleNo;
	private String vehicleColor;
	private String vehicleModal;
	private String vehicleType;
	private Integer recidentId;
	private String recidentName;
	private Integer profileTypeId;
	private Integer unitId;
	private Integer vehicleId;
	private Integer vehicleStatusId;
	private Integer recidentProfileStatusId;
	private String unitName;
	private String vehicleNPRImage;
	private boolean active;
	private boolean vehicleSoftLock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRecidentId() {
		return recidentId;
	}

	public void setRecidentId(Integer recidentId) {
		this.recidentId = recidentId;
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

	public String getRecidentName() {
		return recidentName;
	}

	public void setRecidentName(String recidentName) {
		this.recidentName = recidentName;
	}

	public Integer getProfileTypeId() {
		return profileTypeId;
	}

	public void setProfileTypeId(Integer profileTypeId) {
		this.profileTypeId = profileTypeId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getVehicleStatusId() {
		return vehicleStatusId;
	}

	public void setVehicleStatusId(Integer vehicleStatusId) {
		this.vehicleStatusId = vehicleStatusId;
	}

	public Integer getRecidentProfileStatusId() {
		return recidentProfileStatusId;
	}

	public void setRecidentProfileStatusId(Integer recidentProfileStatusId) {
		this.recidentProfileStatusId = recidentProfileStatusId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getVehicleNPRImage() {
		return vehicleNPRImage;
	}

	public void setVehicleNPRImage(String vehicleNPRImage) {
		this.vehicleNPRImage = vehicleNPRImage;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isVehicleSoftLock() {
		return vehicleSoftLock;
	}

	public void setVehicleSoftLock(boolean vehicleSoftLock) {
		this.vehicleSoftLock = vehicleSoftLock;
	}

	@Override
	public String toString() {
		return "Vehicle [recidentId=" + recidentId + ", vehicleNo=" + vehicleNo + ", vehicleColor=" + vehicleColor
				+ ", vehicleModal=" + vehicleModal + ", vehicleType=" + vehicleType + ", recidentName=" + recidentName
				+ ", profileTypeId=" + profileTypeId + ", unitId=" + unitId + ", vehicleId=" + vehicleId
				+ ", vehicleStatusId=" + vehicleStatusId + ", recidentProfileStatusId=" + recidentProfileStatusId
				+ ", unitName=" + unitName + ", vehicleNPRImage=" + vehicleNPRImage + ", active=" + active
				+ ", vehicleSoftLock=" + vehicleSoftLock + "]";
	}

}
