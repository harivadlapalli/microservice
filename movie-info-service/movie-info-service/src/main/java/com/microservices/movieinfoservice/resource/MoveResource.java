package com.microservices.movieinfoservice.resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.movieinfoservice.model.Movie;

@RestController
@RequestMapping("/movies")
public class MoveResource {
	 
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		return new Movie(movieId,"Test name");
		
	}
}
