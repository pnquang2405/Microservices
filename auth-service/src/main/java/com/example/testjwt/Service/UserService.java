package com.example.testjwt.Service;

import com.example.testjwt.entity.User;
import com.example.testjwt.model.UserPrincipal;

public interface UserService {
	User createUser(User user);
	UserPrincipal findByUsername(String username);
}
