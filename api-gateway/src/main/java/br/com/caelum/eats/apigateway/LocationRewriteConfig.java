package br.com.caelum.eats.apigateway;

import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;

@Configuration
public class LocationRewriteConfig {

	@Bean
	LocationRewriteFilter locationRewriteFilter() {
		return new LocationRewriteFilter() {
			@Override
			public boolean shouldFilter() {
				RequestContext ctx = RequestContext.getCurrentContext();
				int statusCode = ctx.getResponseStatusCode();
				return HttpStatus.valueOf(statusCode).is2xxSuccessful() 
						|| HttpStatus.valueOf(statusCode).is3xxRedirection();
			}
		};
	}
	
}
