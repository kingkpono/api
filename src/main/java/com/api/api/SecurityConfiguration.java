package com.api.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@SuppressWarnings("deprecation")
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.ldapAuthentication()
        .userDnPatterns("uid={0},ou=people")
        .groupSearchBase("ou=groups")
        .contextSource()
          .url("ldap://localhost:8389/dc=springframework,dc=org")
          .and()
        .passwordCompare()
          .passwordEncoder(new LdapShaPasswordEncoder())
          .passwordAttribute("userPassword");
		  auth.parentAuthenticationManager(authenticationManagerBean());
	}
	

	
	
	
	
	  @Override
	protected void configure(HttpSecurity http) throws Exception {
		  http
	      .csrf().disable().
	         authorizeRequests().
	         antMatchers("/api/v1/accounts").permitAll()
	         .antMatchers("/api/v1/accounts/*").permitAll()
	         .antMatchers("/api/auth/**").permitAll()
	         .anyRequest().authenticated()
	         .and().httpBasic();
	}
}
