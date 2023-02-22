package br.com.jarvis.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author eduardo.thums
 */
public enum LoginType {
	NORMAL,
	FACEBOOK,
	APPLE,
	ACCESS_CODE,
	GOOGLE;

	public static LoginType from(UsernamePasswordAuthenticationToken authentication) {
		final LoginType type;

		if (authentication.getPrincipal().toString().contains("facebook=true")) {
			type = FACEBOOK;
		} else if (authentication.getPrincipal().toString().contains("apple=true")) {
			type = APPLE;
		} else if (authentication.getPrincipal().toString().contains("accessCode=true")) {
			type = ACCESS_CODE;
		} else if (authentication.getPrincipal().toString().contains("google=true")) {
			type = GOOGLE;
		} else {
			type = NORMAL;
		}

		return type;
	}
}
