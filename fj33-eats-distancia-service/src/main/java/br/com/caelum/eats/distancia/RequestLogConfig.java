package br.com.caelum.eats.distancia;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogConfig {

	@Bean
	CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setIncludeQueryString(true);
		return loggingFilter;
	}
	
}
