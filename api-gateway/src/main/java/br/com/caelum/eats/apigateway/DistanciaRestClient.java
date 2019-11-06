package br.com.caelum.eats.apigateway;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	Map<String, Object> porCepEId(String cep, Long restauranteId) {
		String url = distanciaServiceUrl + "/restaurantes/" + cep + "/restaurante/"+ restauranteId;
		return restTemplate.getForObject(url, Map.class);
	}
	
}