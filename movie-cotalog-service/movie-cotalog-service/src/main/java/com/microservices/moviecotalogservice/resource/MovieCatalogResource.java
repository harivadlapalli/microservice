package com.microservices.moviecotalogservice.resource;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.moviecotalogservice.models.CatalogItem;
import com.microservices.moviecotalogservice.models.Movie;
import com.microservices.moviecotalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;	

	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable ("userId") String userId){
		
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId,UserRating.class);
		
		return ratings.getUserRating().stream().map(rating -> {
		  Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			 return  new CatalogItem(movie.getName(),"desc",rating.getRating());
		 })
		  .collect(Collectors.toList());	
		
		
		public List<CatalogItem> getFallbackCatalog(@PathVariable ("userId") String userId){
			return Arrays.asList(neww CatalogItem("No Movie", "", 0));
		}
					
					
	}

}
