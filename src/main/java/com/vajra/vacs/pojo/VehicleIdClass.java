package com.vajra.vacs.pojo;

import java.io.Serializable;

public class VehicleIdClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8815151085613887471L;

	private Integer id;
	private String vehicleNo;

	// default constructor
	public VehicleIdClass() {
		super();
	}

	public VehicleIdClass(Integer id, String vehicleNo) {
		this.id = id;
		this.vehicleNo = vehicleNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((vehicleNo == null) ? 0 : vehicleNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleIdClass other = (VehicleIdClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (vehicleNo == null) {
			if (other.vehicleNo != null)
				return false;
		} else if (!vehicleNo.equals(other.vehicleNo))
			return false;
		return true;
	}

	// equals() and hashCode()

}