package com.vajra.vacs.pojo;

import java.io.Serializable;
import javax.annotation.Generated;

public class VehicleMinimized implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1658178505759774474L;

	private String RecidentName;
	private Integer ProfileTypeId;
	private String UnitName;
	private String VehicleNo;
	private Integer VehicleStatusId;
	private String VehicleSoftLock;
	private Integer RecidentProfileStatusId;

	@Generated("SparkTools")
	private VehicleMinimized(Builder builder) {
		this.RecidentName = builder.RecidentName;
		this.ProfileTypeId = builder.ProfileTypeId;
		this.UnitName = builder.UnitName;
		this.VehicleNo = builder.VehicleNo;
		this.VehicleStatusId = builder.VehicleStatusId;
		this.VehicleSoftLock = builder.VehicleSoftLock;
		this.RecidentProfileStatusId = builder.RecidentProfileStatusId;
	}

	public String getRecidentName() {
		return RecidentName;
	}

	public void setRecidentName(String recidentName) {
		RecidentName = recidentName;
	}

	public Integer getProfileTypeId() {
		return ProfileTypeId;
	}

	public void setProfileTypeId(Integer profileTypeId) {
		ProfileTypeId = profileTypeId;
	}

	public String getUnitName() {
		return UnitName;
	}

	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getVehicleNo() {
		return VehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		VehicleNo = vehicleNo;
	}

	public Integer getVehicleStatusId() {
		return VehicleStatusId;
	}

	public void setVehicleStatusId(Integer vehicleStatusId) {
		VehicleStatusId = vehicleStatusId;
	}

	public String getVehicleSoftLock() {
		return VehicleSoftLock;
	}

	public void setVehicleSoftLock(String vehicleSoftLock) {
		VehicleSoftLock = vehicleSoftLock;
	}

	public Integer getRecidentProfileStatusId() {
		return RecidentProfileStatusId;
	}

	public void setRecidentProfileStatusId(Integer recidentProfileStatusId) {
		RecidentProfileStatusId = recidentProfileStatusId;
	}

	/**
	 * Creates builder to build {@link VehicleMinimized}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link VehicleMinimized}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String RecidentName;
		private Integer ProfileTypeId;
		private String UnitName;
		private String VehicleNo;
		private Integer VehicleStatusId;
		private String VehicleSoftLock;
		private Integer RecidentProfileStatusId;

		private Builder() {
		}

		public Builder withRecidentName(String RecidentName) {
			this.RecidentName = RecidentName;
			return this;
		}

		public Builder withProfileTypeId(Integer ProfileTypeId) {
			this.ProfileTypeId = ProfileTypeId;
			return this;
		}

		public Builder withUnitName(String UnitName) {
			this.UnitName = UnitName;
			return this;
		}

		public Builder withVehicleNo(String VehicleNo) {
			this.VehicleNo = VehicleNo;
			return this;
		}

		public Builder withVehicleStatusId(Integer VehicleStatusId) {
			this.VehicleStatusId = VehicleStatusId;
			return this;
		}

		public Builder withVehicleSoftLock(String VehicleSoftLock) {
			this.VehicleSoftLock = VehicleSoftLock;
			return this;
		}

		public Builder withRecidentProfileStatusId(Integer RecidentProfileStatusId) {
			this.RecidentProfileStatusId = RecidentProfileStatusId;
			return this;
		}

		public VehicleMinimized build() {
			return new VehicleMinimized(this);
		}
	}

}
