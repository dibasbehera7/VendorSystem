package com.dibasb.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.dibasb.entities.UserMs;
import com.dibasb.entities.UserRole;


public class AuthenticatedUser extends User{

	private static final long serialVersionUID = 1L;
	private UserMs user;
	
	public AuthenticatedUser(UserMs user)
	{
		super(user.getEmail(), user.getPassword(), getAuthorities(user));
		this.user = user;
	}
	
	
	public UserMs getUser()
	{
		return user;
	}
	
	
	private static Collection<? extends GrantedAuthority> getAuthorities(UserMs user)
	{
		Set<String> roleAndPermissions = new HashSet<>();
		List<UserRole> roles = user.getRoles();
		
		for (UserRole role : roles)
		{
			roleAndPermissions.add(role.toString());
		}
		String[] roleNames = new String[roleAndPermissions.size()];
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
		return authorities;
	}
}
