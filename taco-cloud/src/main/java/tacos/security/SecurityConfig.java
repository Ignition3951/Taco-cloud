package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			tacos.model.User user = userRepo.findByUsername(username);
			if (user != null)
				return user;
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeRequests().requestMatchers("/design", "/orders/").hasRole("USER")
				.requestMatchers("/", "/**").permitAll().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/design").and().logout()
				.logoutSuccessUrl("/").and().csrf().disable().build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**");
	}


//	@Bean
//	public UserDetailsService userDetailsService(UserRepository userRepo) {
//	 return username -> {
//	 tacos.model.User user = userRepo.findByUsername(username);
//	 if (user != null) return user;
//	 throw new UsernameNotFoundException("User '" + username + "' not found");
//	 };
//	}

}
