package com.example.testjwt.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.testjwt.model.UserPrincipal;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserService userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println(username);
        UserPrincipal user = userMapper.findByUsername(username);
        System.out.println(user);
        if(user.getUserId() != null){
        	System.out.println("o day khong null");
            List<GrantedAuthority> authorities = new ArrayList<>();

            return new User(user.getUsername(),user.getPassword(),authorities);
        }
        throw  new UsernameNotFoundException("Username : " + username + " not found");
    }

}
