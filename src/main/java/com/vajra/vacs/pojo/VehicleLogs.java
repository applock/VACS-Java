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
@Table(name = "vehicle_logs")
public class VehicleLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 622764773842018934L;

	@Override
	public String toString() {
		return "VehicleLogs [VehicleId=" + VehicleId + ", ResidentId=" + ResidentId + ", VehicleNo=" + VehicleNo
				+ ", TransactionDateTime=" + TransactionDateTime + ", TransactionType=" + TransactionType
				+ ", IPAdderess=" + IPAdderess + ", Port=" + Port + ", SnapURL=" + SnapURL + ", IsSync=" + IsSync + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(accessMode = AccessMode.READ_ONLY)
	private Integer log_id;
	private Integer VehicleId;
	private Integer ResidentId;
	private String VehicleNo;
	private String TransactionDateTime;
	private String TransactionType;
	private String IPAdderess;
	private Integer Port;
	private String SnapURL;
	private Boolean IsSync;

	public Integer getVehicleId() {
		return VehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		VehicleId = vehicleId;
	}

	public Integer getResidentId() {
		return ResidentId;
	}

	public void setResidentId(Integer residentId) {
		ResidentId = residentId;
	}

	public String getVehicleNo() {
		return VehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		VehicleNo = vehicleNo;
	}

	public String getTransactionDateTime() {
		return TransactionDateTime;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		TransactionDateTime = transactionDateTime;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getIPAdderess() {
		return IPAdderess;
	}

	public void setIPAdderess(String iPAdderess) {
		IPAdderess = iPAdderess;
	}

	public Integer getPort() {
		return Port;
	}

	public void setPort(Integer port) {
		Port = port;
	}

	public String getSnapURL() {
		return SnapURL;
	}

	public void setSnapURL(String snapURL) {
		SnapURL = snapURL;
	}

	public Boolean getIsSync() {
		return IsSync;
	}

	public void setIsSync(Boolean isSync) {
		IsSync = isSync;
	}

}
