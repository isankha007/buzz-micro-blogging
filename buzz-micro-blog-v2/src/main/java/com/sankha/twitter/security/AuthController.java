package com.sankha.twitter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.twitter.response.ApiResponse;
import com.sankha.twitter.security.jwt.JwtUtil;
import com.sankha.twitter.user.CustomUserDetailsService;
import com.sankha.twitter.user.UserEntity;
import com.sankha.twitter.user.UserRepository;
import com.sankha.twitter.user.UserService;
import com.sankha.twitter.user.dto.CreateUserRequestDto;
import com.sankha.twitter.user.dto.LoginDto;

@RestController
@RequestMapping("/tweets")
public class AuthController {
	
	@Autowired
	private UserService userCrudService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private ApiResponse apiResponse;
	@Autowired
	private LoginDto loginDto;
	@Autowired
	private UserRepository userRepository;
	
	

	@PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> register(@RequestBody CreateUserRequestDto user) throws Exception
	{
		UserEntity newUser = userCrudService.createNewUser(user);	    
	    apiResponse.setMessage("User created!");
	    apiResponse.setData(newUser);
		
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> authenticateUser(@RequestBody UserEntity user)
	{
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		authenticationManager.authenticate(auth); 
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		UserEntity LoggedInUser = userRepository.findByUserName(user.getUserName());
		
		loginDto.setJwt(jwt);
		loginDto.setUsername(user.getUserName());
		loginDto.setUserid(LoggedInUser.getUserId());
		
		apiResponse.setMessage("Auth Token!");
		apiResponse.setData(loginDto);
		 
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.OK);		 
	}
}