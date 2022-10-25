package com.example.testjwt.JWT;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.testjwt.model.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager;
	private JwtTokenProvider jwtTokenProvider;
	
	
	public JwtRequestFilter(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
       

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserPrincipal signInRequest = new ObjectMapper().readValue(request.getInputStream(), UserPrincipal.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
			return authManager.authenticate(authentication);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = jwtTokenProvider.generateToken(auth.getName());

		HashMap<String, String> tokenJson = new HashMap<String, String>();
		tokenJson.put("token", token);

		
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(new ObjectMapper().writeValueAsString(tokenJson));
		response.setStatus(HttpStatus.OK.value());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
//		ErrorResponse res;
//		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//		response.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//		if (failed.getCause() instanceof BadCredentialsException) {
//			res = new ErrorResponse("ERR.BADCREDENTIAL", "user or password is invalid");
//		} else if (failed.getCause().getCause() instanceof AccountLockedException) {
//			res = new ErrorResponse("ERR.ACC_LOCK", " this account is locked");
//		} else {
//			res = new ErrorResponse("ERR.UNAUTHORIZED", failed.getMessage());
//		}
//		response.getWriter().write(new ObjectMapper().writeValueAsString(res));
//		System.out.println("Cause " + failed.getCause());

//        super.unsuccessfulAuthentication(request, response, failed);
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(new ObjectMapper().writeValueAsString("wrong username or password"));
	}
}
