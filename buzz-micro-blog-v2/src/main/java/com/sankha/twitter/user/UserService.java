package com.sankha.twitter.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sankha.twitter.user.dto.CreateUserRequestDto;
import com.sankha.twitter.user.dto.UserResponseDto;

@Service
public class UserService {
  @Autowired	
  private UserRepository repository;
  @Autowired
  private ModelMapper modelMapper;

  
  public UserResponseDto createUser(CreateUserRequestDto request) {
	  var user = modelMapper.map(request, UserEntity.class);
     // user.setPassword(passwordEncoder.encode(request.getPassword()));
      var savedUser = repository.save(user);
      var response = modelMapper.map(savedUser, UserResponseDto.class);
      // OPTION 1: Server side token
      // var token = authTokenService.createToken(savedUser);
      // response.setToken(token);
      // OPTION 2: JWT
     // var token = jwtService.createJwt(savedUser.getUsername());
      //response.setToken(token);
      return response;
  }
  
  
}
