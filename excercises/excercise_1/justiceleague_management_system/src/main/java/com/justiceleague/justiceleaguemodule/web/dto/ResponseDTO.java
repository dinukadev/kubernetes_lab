package com.justiceleague.justiceleaguemodule.web.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the output given out to the calling party as a json
 * representation.
 * 
 * @author dinuka
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class ResponseDTO {

	@NotNull
	@JsonProperty("status")
	private Status status;

	@JsonProperty("message")
	private String message;

	public ResponseDTO(@JsonProperty("status") Status status, @JsonProperty("message") String message) {
		this.status = status;
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public enum Status {
		SUCCESS, FAIL
	}
}
