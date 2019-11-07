package br.com.caelum.eats.apigateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
class DistanciaRestClient {

	private RestTemplate restTemplate;
	private String distanciaServiceUrl;
	
	public DistanciaRestClient(RestTemplate restTemplate, 
								@Value("${configuracao.distancia.service.url}")
								String distanciaServiceUrl) {
		this.restTemplate = restTemplate;
		this.distanciaServiceUrl = distanciaServiceUrl;
	}

	@HystrixCommand(fallbackMethod = "restauranteSemDistancia")
	Map<String, Object> porCepEId(String cep, Long restauranteId) {
		String url = distanciaServiceUrl + "/restaurantes/" + cep + "/restaurante/"+ restauranteId;
		return restTemplate.getForObject(url, Map.class);
	}
	
	Map<String, Object> restauranteSemDistancia(String cep, Long restauranteId) {
		return new HashMap<>();
	}
}
