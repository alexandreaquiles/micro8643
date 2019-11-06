package br.com.caelum.eats.apigateway;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RestauranteComDistanciaController {

	private RestauranteRestClient restauranteRestClient;
	private DistanciaRestClient distanciaRestClient;
	
	public RestauranteComDistanciaController(RestauranteRestClient restauranteRestClient,
			DistanciaRestClient distanciaRestClient) {
		this.restauranteRestClient = restauranteRestClient;
		this.distanciaRestClient = distanciaRestClient;
	}

	@GetMapping("/restaurantes-com-distancia/{cep}/restaurante/{restauranteId}")
	Map<String, Object> porCepEId(@PathVariable("cep") String cep,
								  @PathVariable("restauranteId") Long restauranteId) {
		Map<String, Object> dadosDoRestaurante = restauranteRestClient.porId(restauranteId);
		Map<String, Object> dadosDeDistancia = distanciaRestClient.porCepEId(cep, restauranteId);
		dadosDoRestaurante.putAll(dadosDeDistancia);
		return dadosDoRestaurante;
	}
	
}
