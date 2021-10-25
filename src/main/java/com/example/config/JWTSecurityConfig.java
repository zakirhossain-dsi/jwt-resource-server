package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests
					.antMatchers(HttpMethod.GET, "/foos/**").hasAuthority("SCOPE_profile")
					.antMatchers(HttpMethod.GET, "/foos").hasAuthority("SCOPE_mange")
					.anyRequest().authenticated())
			.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		http.csrf().disable();
	}
	*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers(HttpMethod.GET, "/foos/**").hasRole("app-user")
				.antMatchers(HttpMethod.GET, "/foos").hasRole("app-user")
				.anyRequest().authenticated()

		).oauth2ResourceServer(oauth2ResourceServer ->
				oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
		);

		http.csrf().disable();
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
		return jwtConverter;
	}
}
