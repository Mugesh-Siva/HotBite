package com.hexaware.hotbyte.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
			@Autowired
			UserDetailsService   userDetailsService;
			
			@Bean
			public PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
			}

				@Bean
			public     AuthenticationProvider   getAuthProvider() {
				DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
				dao.setUserDetailsService(userDetailsService);
				dao.setPasswordEncoder(passwordEncoder());
				return  dao;
			}
			
			@Bean
			public org.springframework.security.web.SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
				return http.csrf(csrf -> csrf.disable())
						.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
						.formLogin(org.springframework.security.config.Customizer.withDefaults())
						.oauth2Login(org.springframework.security.config.Customizer.withDefaults())
						.build();
			}
}
