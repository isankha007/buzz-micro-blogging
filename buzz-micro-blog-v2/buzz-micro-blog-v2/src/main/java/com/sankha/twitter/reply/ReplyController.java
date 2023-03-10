package com.sankha.twitter.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.twitter.response.ApiResponse;

@RestController
@RequestMapping("/tweets")
public class ReplyController {
	@Autowired
	ReplyService replyService;
	@Autowired
	ApiResponse apiResponse;
	
	@PostMapping(path = "/tweet/{tweet_id}/reply", produces = "application/json")
	public ResponseEntity<Object> userMakesCommentAtTweet(
			@RequestBody Reply reply, 
			Authentication authentication,
			@PathVariable("tweet_id") long tweet_id 
	) throws Exception{
		Reply savedComment = replyService.userMakesNewCommentAtTweet(authentication, tweet_id, reply);		
		apiResponse.setData(savedComment);
		apiResponse.setMessage("Comment crated");
		return new ResponseEntity<>(apiResponse.getBodyResponse(),HttpStatus.OK);
	}
}
