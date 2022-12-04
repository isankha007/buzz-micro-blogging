package com.sankha.twitter.tweet;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sankha.twitter.tweet.dto.CreateTweetDto;
import com.sankha.twitter.tweet.dto.TweetResposeDto;
import com.sankha.twitter.user.UserEntity;
import com.sankha.twitter.user.UserRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepo;
	@Autowired
	private UserRepository userRepo;
	
	  @Autowired
	  private ModelMapper modelMapper;
	  
	  
	  public List<Tweet> getFeed(UserEntity loggedInUser)
		{
			Tweet latestUserTweet = null;
			List<Tweet> userTweets = tweetRepo.findLatestTweetByUser(loggedInUser.getUserId());
			if(userTweets.size() > 0) latestUserTweet = userTweets.get(0);

			List<Tweet> followTweets = tweetRepo.findTweetsThatUserFollows(loggedInUser);
			/*if(latestUserTweet != null && latestUserTweet.getUpdated().after(timestampUtil.oneMinuteBackTimestamp()))
			{
				followTweets.add(0,latestUserTweet);
			}*/
			return followTweets;
		}
	  
	  public Tweet createTweet(Authentication authentication, Tweet newTweet)
		{		
	        UserEntity LoggedInUser = userRepo.findByUsername(authentication.getName());
	        newTweet.setTweetAuthor(LoggedInUser);
	      
	       // Timestamp currentTimestamp = timestampUtil.currentTimestamp();
	       // newTweet.setTweet_created_at(currentTimestamp );
	       // newTweet.setTweet_updated_at(currentTimestamp );        
	        return tweetRepo.save(newTweet);
		}
	
	  
	  
	  
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
