package br.com.caelum.eats.distancia;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
class RestaurantesController {
	
	private RestauranteRepository repo;
	
	@PostMapping("/restaurantes")
	ResponseEntity<Restaurante> adiciona(@RequestBody Restaurante restaurante, UriComponentsBuilder uriBuilder) {
		Restaurante salvo = repo.insert(restaurante);
		log.info("Inseriu novo restaurante de id: {}", salvo.getId());
		UriComponents uriComponents = uriBuilder.path("/restaurantes/{id}").buildAndExpand(salvo.getId());
		URI location = uriComponents.toUri();
		return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON).body(salvo);
	}
	
	@PutMapping("/restaurantes/{id}")
	Restaurante atualiza(@PathVariable("id") Long id, @RequestBody Restaurante restaurante) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException();
		}
		Restaurante salvo = repo.save(restaurante);
		log.info("Atualizou o restaurante de id: {}", salvo.getId());
		return salvo;
	}
	
}














