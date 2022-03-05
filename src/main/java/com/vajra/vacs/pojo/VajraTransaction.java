package com.vajra.vacs.pojo;

import java.io.Serializable;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VajraTransaction implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6245204158980080445L;

	@JsonProperty("VehicleId")
	public int vehicleId;
	@JsonProperty("ResidentId")
	public int residentId;
	@JsonProperty("VehicleNo")
	public String vehicleNo;
	@JsonProperty("TransactionDateTime")
	public String transactionDateTime;
	@JsonProperty("TransactionType")
	public String transactionType;
	@JsonProperty("IPAdderess")
	public String iPAdderess;
	@JsonProperty("Port")
	public int port;
	@JsonProperty("SnapStringBase64")
	public String snapStringBase64;
	@JsonProperty("IsSync")
	public boolean isSync;

	@Generated("SparkTools")
	private VajraTransaction(Builder builder) {
		this.vehicleId = builder.vehicleId;
		this.residentId = builder.residentId;
		this.vehicleNo = builder.vehicleNo;
		this.transactionDateTime = builder.transactionDateTime;
		this.transactionType = builder.transactionType;
		this.iPAdderess = builder.iPAdderess;
		this.port = builder.port;
		this.snapStringBase64 = builder.snapStringBase64;
		this.isSync = builder.isSync;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getResidentId() {
		return residentId;
	}

	public void setResidentId(int residentId) {
		this.residentId = residentId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getiPAdderess() {
		return iPAdderess;
	}

	public void setiPAdderess(String iPAdderess) {
		this.iPAdderess = iPAdderess;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSnapStringBase64() {
		return snapStringBase64;
	}

	public void setSnapStringBase64(String snapStringBase64) {
		this.snapStringBase64 = snapStringBase64;
	}

	public boolean isSync() {
		return isSync;
	}

	public void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	@Override
	public String toString() {
		return "VajraTransaction [vehicleId=" + vehicleId + ", residentId=" + residentId + ", vehicleNo=" + vehicleNo
				+ ", transactionDateTime=" + transactionDateTime + ", transactionType=" + transactionType
				+ ", iPAdderess=" + iPAdderess + ", port=" + port + ", snapStringBase64=" + snapStringBase64
				+ ", isSync=" + isSync + "]";
	}

	/**
	 * Creates builder to build {@link VajraTransaction}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link VajraTransaction}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private int vehicleId;
		private int residentId;
		private String vehicleNo;
		private String transactionDateTime;
		private String transactionType;
		private String iPAdderess;
		private int port;
		private String snapStringBase64;
		private boolean isSync;

		private Builder() {
		}

		public Builder withVehicleId(int vehicleId) {
			this.vehicleId = vehicleId;
			return this;
		}

		public Builder withResidentId(int residentId) {
			this.residentId = residentId;
			return this;
		}

		public Builder withVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
			return this;
		}

		public Builder withTransactionDateTime(String transactionDateTime) {
			this.transactionDateTime = transactionDateTime;
			return this;
		}

		public Builder withTransactionType(String transactionType) {
			this.transactionType = transactionType;
			return this;
		}

		public Builder withIPAdderess(String iPAdderess) {
			this.iPAdderess = iPAdderess;
			return this;
		}

		public Builder withPort(int port) {
			this.port = port;
			return this;
		}

		public Builder withSnapStringBase64(String snapStringBase64) {
			this.snapStringBase64 = snapStringBase64;
			return this;
		}

		public Builder withIsSync(boolean isSync) {
			this.isSync = isSync;
			return this;
		}

		public VajraTransaction build() {
			return new VajraTransaction(this);
		}
	}

}
