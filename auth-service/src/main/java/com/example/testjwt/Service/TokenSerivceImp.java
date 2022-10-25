package com.example.testjwt.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testjwt.Repository.TokenRepository;
import com.example.testjwt.entity.Token;

@Service
public class TokenSerivceImp implements TokenService{

	@Autowired
	TokenRepository tokenRepository;
	
	@Override
	public Token createToken(Token token) {
		return tokenRepository.saveAndFlush(token);
	}
	
	@Override
	public Token findByToken(String token) {
	    return tokenRepository.findByToken(token);
	}

}
