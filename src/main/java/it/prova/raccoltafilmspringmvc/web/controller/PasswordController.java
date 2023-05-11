package it.prova.raccoltafilmspringmvc.web.controller;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.prova.raccoltafilmspringmvc.dto.PasswordDTO;
import it.prova.raccoltafilmspringmvc.service.UtenteService;
import it.prova.raccoltafilmspringmvc.validation.ValidationWithPassword;

@Controller
@RequestMapping("/resetpassword")
public class PasswordController {
	
	@Autowired
	private UtenteService utenteService;

	@GetMapping(value = "/prepareresetpassword")
	public String resetPassword(Model model) {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			model.addAttribute("reset_password_attr", new PasswordDTO());
			return "resetpassword";
		}
		return "index";
		
		}		
	
	@PostMapping(value = "/executeresetpassword")
	public String resetPassword(@Validated(ValidationWithPassword.class) @ModelAttribute("reset_password_attr") PasswordDTO passwordDTO, BindingResult result, RedirectAttributes redirect) {
		if (StringUtils.isNoneBlank(passwordDTO.getNuovaPassword()) && !passwordDTO.getNuovaPassword().equals(passwordDTO.getConfermaPassword())){
			result.rejectValue("confermaPassword", "password.diverse");
			
		}
		if (result.hasErrors()) {
			return "resetpassword";
		}
		try {
			utenteService.setPassword(passwordDTO.getVecchiaPassword(), passwordDTO.getNuovaPassword());
			
		}
		catch (RuntimeException e) {
			result.rejectValue("vecchia Password", "vecchiapassword.sbagliata");
			return "resetpassword";
		} catch (Exception e) {
			redirect.addFlashAttribute("infoMessage", "Attenzione! utente non trovato!");
			return ("redirect:/logout");
			
		}
		return "redirect:/executeLogout";
		
	}
	}



