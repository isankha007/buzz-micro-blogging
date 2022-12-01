package com.sankha.twitter.tweet;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.twitter.tweet.dto.CreateTweetDto;
import com.sankha.twitter.tweet.dto.TweetResposeDto;

@RestController
@RequestMapping("/tweets")
public class TweetCotroller {
	@Autowired
    private TweetService tweetService;
    
    @PostMapping("")
    public ResponseEntity<TweetResposeDto> createTweet(
            @RequestBody CreateTweetDto request
    ) {
        var createdTweet = tweetService.createTweet(request);
        return ResponseEntity.created(URI.create("/tweet/" + createdTweet.getTweetId())).body(createdTweet);
    }
}
