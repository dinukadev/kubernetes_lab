package com.justiceleague.justiceleaguemodule.web.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This class will hold the justice league memger details that will be received
 * as a json input.
 * 
 * @author dinuka
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class JusticeLeagueMemberDTO {

	@NotNull
	@JsonProperty("name")
	private String name;

	@NotNull
	@JsonProperty("superPower")
	private String superPower;

	@JsonProperty("location")
	private String location;

	public JusticeLeagueMemberDTO(@JsonProperty("name") String name, @JsonProperty("superPower") String superPower,
			@JsonProperty("location") String location) {
		this.name = name;
		this.superPower = superPower;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuperPower() {
		return superPower;
	}

	public void setSuperPower(String superPower) {
		this.superPower = superPower;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
