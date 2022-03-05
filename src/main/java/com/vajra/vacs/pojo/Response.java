package com.vajra.vacs.pojo;

import java.io.Serializable;

import javax.annotation.Generated;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8192712008988958381L;
	
	private String code;
	private String message;

	@Generated("SparkTools")
	private Response(Builder builder) {
		this.code = builder.code;
		this.message = builder.message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Creates builder to build {@link Response}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Response}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String code;
		private String message;

		private Builder() {
		}

		public Builder withCode(String code) {
			this.code = code;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Response build() {
			return new Response(this);
		}
	}

}
