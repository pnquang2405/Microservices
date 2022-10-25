package com.example.testjwt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testjwt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String username);
}
