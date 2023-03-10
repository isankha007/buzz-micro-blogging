package com.sankha.twitter.tweet;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sankha.twitter.like.LikeEntity;
import com.sankha.twitter.reply.Reply;
import com.sankha.twitter.user.UserEntity;
import com.sankha.twitter.media.Media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tweetId;
	
	private String text;
	private Timestamp created;
	private Timestamp updated;
	
	@ManyToOne
	private UserEntity tweetAuthor;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="replies")
	private List<Reply> replies;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="likes")
	private List<LikeEntity> likes;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="media")
	private List<Media> medias;
	
	
	
	
	
}
