package com.vajra.vacs.pojo;

import java.io.Serializable;

public class VacsInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7803606290782998053L;

	private String msg;
	private String source;
	private String destination;
	private String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "VacsInput [msg=" + msg + ", source=" + source + ", destination=" + destination + ", code=" + code + "]";
	}

}
