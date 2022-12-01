package com.sankha.twitter.tweet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sankha.twitter.tweet.dto.CreateTweetDto;
import com.sankha.twitter.tweet.dto.TweetResposeDto;
import com.sankha.twitter.user.UserRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepo;
	@Autowired
	private UserRepository userRepo;
	
	  @Autowired
	  private ModelMapper modelMapper;
	
	public TweetResposeDto createTweet(CreateTweetDto tweet) {
		 var tweetTemp = modelMapper.map(tweet, Tweet.class);
		 var user= userRepo.findById(1L);
		 tweetTemp.setTweetAuthor(user.get());
	     // user.setPassword(passwordEncoder.encode(request.getPassword()));
	      var savedTweet = tweetRepo.save(tweetTemp);
	      var response = modelMapper.map(savedTweet, TweetResposeDto.class);
	      
	      return response;
	}
	
 
}
