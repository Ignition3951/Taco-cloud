package tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;
import tacos.model.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegisrationController {
	
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public RegisrationController(UserRepository userRepository ,PasswordEncoder passwordEncoder) {
		this.passwordEncoder=passwordEncoder;
		this.userRepository=userRepository;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm registrationForm) {
		userRepository.save(registrationForm.toUser(passwordEncoder));
		return "redirect:/login";
	}
	

}
