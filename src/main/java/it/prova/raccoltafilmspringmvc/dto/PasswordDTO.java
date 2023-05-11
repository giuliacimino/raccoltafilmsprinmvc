package it.prova.raccoltafilmspringmvc.dto;

import javax.validation.constraints.NotBlank;

public class PasswordDTO {
	@NotBlank(message = "{password.notblank}")
	private String vecchiaPassword;
	
	@NotBlank(message = "{password.notblank}")
	private String nuovaPassword;
	
	private String confermaPassword;
	
	public PasswordDTO() {
		
	}
	
	public PasswordDTO(String vecchiaPassword, String nuovaPassword, String confermaPassword) {
		this.vecchiaPassword=vecchiaPassword;
		this.nuovaPassword= nuovaPassword;
		this.confermaPassword=confermaPassword;
		
	}

	public String getVecchiaPassword() {
		return vecchiaPassword;
	}

	public void setVecchiaPassword(String vecchiaPassword) {
		this.vecchiaPassword = vecchiaPassword;
	}

	public String getNuovaPassword() {
		return nuovaPassword;
	}

	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}
	
	
	

}
