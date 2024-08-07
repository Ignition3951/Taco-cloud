package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tacos.data.UserRepository;

@Configuration
@EnableWebMvc
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

//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity.authorizeRequests().requestMatchers("/design", "/orders/").hasRole("USER")
//				.requestMatchers(HttpMethod.POST, "/data-api/ingredients").hasAuthority("SCOPE_writeIngredients")
//				.requestMatchers(HttpMethod.DELETE, "/data-api/ingredients").hasAuthority("SCOPE_deleteIngredients")
//				.requestMatchers("/", "/**").permitAll().and().formLogin().loginPage("/login")
//				.defaultSuccessUrl("/design").and().logout().logoutSuccessUrl("/").and()
//				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
//				.oauth2Client(Customizer.withDefaults()).csrf().disable().build();
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests -> {
			try {
				requests.requestMatchers("/register").permitAll().requestMatchers("/**").authenticated().and()
						.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
						.oauth2Client(Customizer.withDefaults());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return httpSecurity.build();
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
