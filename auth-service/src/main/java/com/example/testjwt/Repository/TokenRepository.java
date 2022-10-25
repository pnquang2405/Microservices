package com.example.testjwt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testjwt.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{
	Token findByToken(String token);
}
