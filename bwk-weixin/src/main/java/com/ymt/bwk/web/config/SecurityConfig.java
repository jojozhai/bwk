/**
 * 
 */
package com.ymt.bwk.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ymt.mirage.user.service.UserService;

/**
 * @author zhailiang
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable() 
//	    	.httpBasic()
//	    		.and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.POST, 
	        		"/comment" //发表评论
	        		).authenticated()
	        .antMatchers(HttpMethod.GET, 
	                "/user/current").authenticated()
	        .anyRequest().permitAll()
	        	.and()
	        .headers().frameOptions().disable();
	    
	}

}
