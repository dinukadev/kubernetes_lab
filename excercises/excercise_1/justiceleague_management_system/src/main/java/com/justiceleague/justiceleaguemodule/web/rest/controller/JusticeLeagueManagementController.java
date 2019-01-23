package com.justiceleague.justiceleaguemodule.web.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.justiceleague.justiceleaguemodule.constants.MessageConstants;
import com.justiceleague.justiceleaguemodule.service.JusticeLeagueMemberService;
import com.justiceleague.justiceleaguemodule.web.dto.JusticeLeagueMemberDTO;
import com.justiceleague.justiceleaguemodule.web.dto.ResponseDTO;

/**
 * This class exposes the REST API for the system.
 * 
 * @author dinuka
 *
 */
@RestController
@RequestMapping("/justiceleague")
public class JusticeLeagueManagementController {

	@Autowired
	private JusticeLeagueMemberService memberService;

	/**
	 * This method will be used to add justice league members to the system.
	 * 
	 * @param justiceLeagueMember
	 *            the justice league member to add.
	 * @return an instance of {@link ResponseDTO} which will notify whether
	 *         adding the member was successful.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseDTO addJusticeLeagueMember(@Valid @RequestBody JusticeLeagueMemberDTO justiceLeagueMember) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
				MessageConstants.MEMBER_ADDED_SUCCESSFULLY);
		try {
			memberService.addMember(justiceLeagueMember);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
}
