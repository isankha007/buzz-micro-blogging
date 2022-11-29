package com.sankha.twitter;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BuzzMicroBlogV2Application {

	public static void main(String[] args) {
		SpringApplication.run(BuzzMicroBlogV2Application.class, args);
	}
	
	  @Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }

}
