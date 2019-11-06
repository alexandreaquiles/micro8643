package br.com.caelum.eats.restaurante;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class DistanciaRestClient {

	private RestTemplate restTemplate;
	private String distanciaServiceUrl;
	
	public DistanciaRestClient(RestTemplate restTemplate, @Value("${distancia.service.url}") String distanciaServiceUrl) {
		this.restTemplate = restTemplate;
		this.distanciaServiceUrl = distanciaServiceUrl;
	}

	void novoRestauranteAprovado(Restaurante restauranteAprovado) {
		RestauranteParaDistancia restauranteParaDistancia = new RestauranteParaDistancia(restauranteAprovado);
		
		String url = distanciaServiceUrl + "/restaurantes";
		ResponseEntity<RestauranteParaDistancia> responseEntity = restTemplate.postForEntity(url, restauranteParaDistancia, RestauranteParaDistancia.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		if(!HttpStatus.CREATED.equals(statusCode)) {
			throw new RuntimeException("Status diferente do esperado: " + statusCode) ;
		}
	}
	
	void restauranteAtualizado(Restaurante restauranteAtualizado) {
		RestauranteParaDistancia restauranteParaDistancia = new RestauranteParaDistancia(restauranteAtualizado);

		String url = distanciaServiceUrl + "/restaurantes/" + restauranteAtualizado.getId();
		restTemplate.put(url, restauranteParaDistancia);
	}
	
}

















