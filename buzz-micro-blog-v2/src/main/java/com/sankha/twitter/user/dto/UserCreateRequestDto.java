package com.sankha.twitter.user.dto;

import java.util.List;


import com.sankha.twitter.follower.Follower;

import lombok.Data;

@Data
public class UserCreateRequestDto {
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private List<Follower> followers;
}
