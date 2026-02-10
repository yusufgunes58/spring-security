package com.example.basicauth.modul;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
ROLE_USER,
ROLE_ADMIN,
ROLE_MOD,
ROLE_FSK;
	
	@Override
	public @Nullable String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}

	
}
