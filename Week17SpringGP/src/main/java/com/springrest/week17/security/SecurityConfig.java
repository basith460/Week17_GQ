package com.springrest.week17.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.springrest.week17.db.AppService;



@Configuration
@EnableWebMvc
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests().requestMatchers("/**").authenticated().and().formLogin();
		return http.build();
	}
	
	@Bean  //hereafter instead of usrDetailsService you use AppService
	public UserDetailsService userDetailsService() {
		return new AppService();
	}
	
	@Bean  // we are going to encrypt using BCrypt method
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean // definittion how the authentication must happen
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(encoder());
		
		provider.setUserDetailsService(userDetailsService());
		
		return provider;
	}
}
