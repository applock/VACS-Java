package com.vajra.vacs.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3449126464232147353L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String numberplate;
	private String ownername;
	private String ownerphone;
	private String owneremail;
	private boolean active;
	private boolean temporarylock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumberPlate() {
		return numberplate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberplate = numberPlate;
	}

	public String getOwnerName() {
		return ownername;
	}

	public void setOwnerName(String ownerName) {
		this.ownername = ownerName;
	}

	public String getOwnerPhone() {
		return ownerphone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerphone = ownerPhone;
	}

	public String getOwnerEmail() {
		return owneremail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.owneremail = ownerEmail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isTemporaryLock() {
		return temporarylock;
	}

	public void setTemporaryLock(boolean temporaryLock) {
		this.temporarylock = temporaryLock;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", numberPlate=" + numberplate + ", ownerName=" + ownername + ", ownerPhone="
				+ ownerphone + ", ownerEmail=" + owneremail + ", active=" + active + ", temporaryLock=" + temporarylock
				+ "]";
	}

}
