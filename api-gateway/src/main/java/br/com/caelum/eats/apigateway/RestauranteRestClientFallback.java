package br.com.caelum.eats.apigateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
class RestauranteRestClientFallback implements RestauranteRestClient {

	@Override
	public Map<String, Object> porId(Long restauranteId) {
		return new HashMap<>();
	}

}
