package com.dibasb.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dibasb.entities.UserMs;
import com.dibasb.repositories.UserRepository;

@Service
//@Transactional
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * @Bean(name="entityManagerFactory") public LocalSessionFactoryBean
	 * sessionFactory() { LocalSessionFactoryBean sessionFactory = new
	 * LocalSessionFactoryBean(); return sessionFactory; }
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserMs user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Email "+username+" not found"));
		return new User(user.getEmail(), user.getPassword(), getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(UserMs user){
		//String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		String userRoles =  user.getRoles().toString();
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
	}
}
