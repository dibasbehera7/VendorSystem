package com.dibasb.config;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dibasb.entities.UserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * @Autowired
	 * 
	 * @Qualifier("springSecurityFilterChain") private Filter
	 * springSecurityFilterChain;
	 */
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
         .userDetailsService(customUserDetailsService)
         .passwordEncoder(passwordEncoder());
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		/*http.csrf()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .permitAll();
		*/
		
		/*http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
	
		http.authorizeRequests()//Ignore the static resource paths & Allow everyone to have access to the root URL
			.antMatchers("/resources/**","/assets/**").permitAll()
			.antMatchers("/","/index").permitAll()
			.anyRequest().authenticated()	//All other URLs should be accessible to authenticated users only
			.and()
			.formLogin().loginPage("/login")
						.defaultSuccessUrl("/home")
						.failureUrl("/login?error")
						.permitAll()
			.and()
			.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe()
						.key(UUID.randomUUID().toString())
						.rememberMeCookieName("remember-me")
						.tokenRepository(persistentTokenRepository())
						.tokenValiditySeconds(24 * 60 * 60)
			.and()
			.exceptionHandling();
		
		//Restrict access to URLs
		http.authorizeRequests().antMatchers("/admin/**").hasRole(UserRole.ADMIN.toString());
		*/
		 http        	
     	.headers()
     		.frameOptions().sameOrigin()
     		.and()
         .authorizeRequests()
         	.antMatchers("/resources/**", "/assets/**").permitAll()
             .antMatchers("/").permitAll()
             .antMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/login")
             .defaultSuccessUrl("/home")
             .failureUrl("/login?error")
             .permitAll()
             .and()
         .logout()
         	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
         	.logoutSuccessUrl("/login?logout")
         	.deleteCookies("my-remember-me-cookie")
             .permitAll()
             .and()
          .rememberMe()
          	//.key("my-secure-key")
          	.rememberMeCookieName("my-remember-me-cookie")
          	.tokenRepository(persistentTokenRepository())
          	.tokenValiditySeconds(24 * 60 * 60)
          	.and()
         .exceptionHandling()
          	;
	}

  PersistentTokenRepository persistentTokenRepository(){
     JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
     tokenRepositoryImpl.setDataSource(dataSource);
     return tokenRepositoryImpl;
  } 
	
	/*
	 * public void getFilters() { FilterChainProxy filterChainProxy =
	 * (FilterChainProxy) springSecurityFilterChain; List<SecurityFilterChain> list
	 * = filterChainProxy.getFilterChains(); list.stream() .flatMap(chain ->
	 * chain.getFilters().stream()) .forEach(filter ->
	 * System.out.println(filter.getClass())); }
	 */

}
