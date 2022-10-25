package com.example.testjwt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testjwt.Service.UserService;
import com.example.testjwt.entity.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public User register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.createUser(user);
	}

//	@Autowired
//	private TokenService tokenService;
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody User user) {
//		UserPrincipal userPrincipal = userService.findByUsername(user.getUserName());
//		if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
//		}
//		Token token = new Token();
//		token.setToken(jwtTokenProvider.generateToken(user.getUserName()));
//		token.setTokenExpDate(jwtTokenProvider.generateExpirationDate());
//		token.setCreateBy(userPrincipal.getUserId());
//		tokenService.createToken(token);
//		return ResponseEntity.ok(token.getToken());
//	}
	
	@GetMapping("/hello")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public ResponseEntity<?> hello(){
	    return ResponseEntity.ok("hello");
	}

}
