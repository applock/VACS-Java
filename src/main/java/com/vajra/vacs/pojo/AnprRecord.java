package com.vajra.vacs.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Entity
@Table(name = "anpr")
public class AnprRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7886816546665645571L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(accessMode = AccessMode.READ_ONLY)
	private Integer id;

	private String vehicleNo;
	private String anprIp;
	private String regNo;

	@CreatedDate
	private Date createdDate;

	public Integer getId() {
		return id;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getAnprIp() {
		return anprIp;
	}

	public void setAnprIp(String anprIp) {
		this.anprIp = anprIp;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public String toString() {
		return "AnprRecord [id=" + id + ", vehicleNo=" + vehicleNo + ", anprIp=" + anprIp + ", regNo=" + regNo
				+ ", createdDate=" + createdDate + "]";
	}

}
