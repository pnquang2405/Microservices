package com.example.testjwt.Service;

import com.example.testjwt.entity.Token;

public interface TokenService {
	Token createToken(Token token);
	Token findByToken(String token);
}
