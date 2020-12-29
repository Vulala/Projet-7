package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class used to define the application authentication. <br>
 * It use the {@link BCryptPasswordEncoder} class to encrypt the password. <br>
 * It extends the {@link WebSecurityConfigurerAdapter} abstract class. <br>
 * The {@link configure} method, define the access right to different views.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;

	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	/**
	 * Configure the permission required or not to access the differents endpoints.
	 * <br>
	 * It also define the template to use as login page. <br>
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
								.antMatchers("/").permitAll()
								.antMatchers("/user/list").permitAll()
								.antMatchers("/admin/home").hasAnyRole("ADMIN")
								.antMatchers("/secure/article-details").hasAnyRole("ADMIN")
								.antMatchers("/bidList/*").authenticated()
								.antMatchers("/curvePoint/*").authenticated()
								.antMatchers("/rating/*").authenticated()
								.antMatchers("/trade/*").authenticated()
								.antMatchers("/ruleName/*").authenticated()
								.and()
								.formLogin()
									.loginPage("/login")
									.permitAll();
	}

}
