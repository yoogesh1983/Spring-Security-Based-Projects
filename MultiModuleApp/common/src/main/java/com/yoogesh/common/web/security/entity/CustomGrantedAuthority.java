package com.yoogesh.common.web.security.entity;

import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -1447095521180508501L;

    private String authority;

	public CustomGrantedAuthority() {}

    public CustomGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
		this.authority = authority;
	}
}
