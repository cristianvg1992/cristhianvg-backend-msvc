package com.cvg.msvc.gateway.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {
	@Value("${config.security.oauth.jwt.key}")
	private String llaveJwt;
	
	@Override
	@SuppressWarnings("unchecked")
	public Mono<Authentication> authenticate(Authentication authentication) {
		System.out.println(llaveJwt);
		return Mono.just(authentication.getCredentials().toString())
				.map(token -> {
					SecretKey llave = Keys.hmacShaKeyFor(Base64.getEncoder().encode(llaveJwt.getBytes()));
					String base64 = Base64.getEncoder().encodeToString(llaveJwt.getBytes());
					return Jwts.parserBuilder().setSigningKey(llave).build().parseClaimsJws(token).getBody();
				})
				.map(claims -> {
					String username = claims.get("user_name", String.class);
					
					List<String> roles = claims.get("authorities", List.class);
					Collection<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
					return new UsernamePasswordAuthenticationToken(username, null, authorities);
					
				});
	}

}
