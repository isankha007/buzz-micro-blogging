package com.sankha.twitter.tweet;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.twitter.response.ApiResponse;
import com.sankha.twitter.tweet.dto.CreateTweetDto;
import com.sankha.twitter.tweet.dto.TweetResposeDto;
import com.sankha.twitter.user.UserEntity;
import com.sankha.twitter.user.UserRepository;
import com.sankha.twitter.user.UserService;

@RestController
@RequestMapping("/tweets")
public class TweetCotroller {
	@Autowired
    private TweetService tweetService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ApiResponse apiResponse;
	
	@GetMapping(path = "/tweet/feed", produces = "application/json")
	public ResponseEntity<Object> getFeed(Authentication authentication)
	{			
        UserEntity LoggedInUser = userRepo.findByUserName(authentication.getName());
        List<Tweet> userFeed = tweetService.getFeed(LoggedInUser);
	    apiResponse.setMessage("User Feed!");
	    apiResponse.setData(userFeed);	    
	   
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.OK);
	}
	
	@PostMapping(path = "/tweet/create", produces = "application/json")
	public ResponseEntity<Object> createTweet(Authentication authentication, @RequestBody Tweet newTweet)
	{			
		Tweet savedTweet = tweetService.createTweet(authentication,newTweet);	    
	    apiResponse.setMessage("Tweet created!");
	    apiResponse.setData(savedTweet);

		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.CREATED);
	}
    
    @PostMapping("")
    public ResponseEntity<TweetResposeDto> createTweet(
            @RequestBody CreateTweetDto request
    ) {
        var createdTweet = tweetService.createTweet(request);
        return ResponseEntity.created(URI.create("/tweet/" + createdTweet.getTweetId())).body(createdTweet);
    }
}
