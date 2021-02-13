package com.ironhack.midtermproject.security;

import com.ironhack.midtermproject.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().ignoringAntMatchers("/withdraw/**");
		http.csrf().ignoringAntMatchers("/deposit/**");
		http.csrf().ignoringAntMatchers("/transaction/**");
		http.csrf().ignoringAntMatchers("/account-holder/**");
		http.csrf().ignoringAntMatchers("/admin/**");
		http.csrf().ignoringAntMatchers("/modify/**");
		http.csrf().ignoringAntMatchers("/third-party/**");
		http.authorizeRequests()
				.mvcMatchers("/checking/**").hasRole("ADMIN")
				.mvcMatchers("/credit-card/**").hasRole("ADMIN")
				.mvcMatchers("/savings/**").hasRole("ADMIN")
				.mvcMatchers("/student-checking/**").hasRole("ADMIN")
				.mvcMatchers("/account-holder/**").hasRole("ADMIN")
				.mvcMatchers("/admin/**").hasRole("ADMIN")
				.mvcMatchers("/modify/**").hasRole("ADMIN")
				.mvcMatchers("/third-party/**").hasRole("THIRD_PARTY")
				.mvcMatchers("/withdraw/**").hasRole("ACCOUNT_HOLDER")
				.mvcMatchers("/deposit/**").hasRole("ACCOUNT_HOLDER")
				.mvcMatchers("/transaction/**").hasRole("ACCOUNT_HOLDER")
				.mvcMatchers("/say-hello").authenticated()
				.anyRequest().permitAll();
	}
}
