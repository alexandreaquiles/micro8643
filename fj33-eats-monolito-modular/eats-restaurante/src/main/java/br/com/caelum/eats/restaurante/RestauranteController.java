package br.com.caelum.eats.restaurante;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
class RestauranteController {

	private RestauranteRepository restauranteRepo;
	private CardapioRepository cardapioRepo;
	private DistanciaRestClient distanciaRestClient;

	@GetMapping("/restaurantes/{id}")
	RestauranteDto detalha(@PathVariable("id") Long id) {
		Restaurante restaurante = restauranteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		return new RestauranteDto(restaurante);
	}
	
	@GetMapping("/parceiros/restaurantes/do-usuario/{username}")
	public RestauranteDto detalhaParceiro(@PathVariable("username") String username) {
		Restaurante restaurante = restauranteRepo.findByUsername(username);
		return new RestauranteDto(restaurante);
	}
	
	@GetMapping("/restaurantes")
	List<RestauranteDto> detalhePorIds(@RequestParam("ids") List<Long> ids) {
		return restauranteRepo.findAllById(ids).stream().map(RestauranteDto::new).collect(Collectors.toList());
	}

	@GetMapping("/parceiros/restaurantes/{id}")
	RestauranteDto detalhaParceiro(@PathVariable("id") Long id) {
		Restaurante restaurante = restauranteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return new RestauranteDto(restaurante);
	}

	@PostMapping("/parceiros/restaurantes")
	Restaurante adiciona(@RequestBody Restaurante restaurante) {
		restaurante.setAprovado(false);
		Restaurante restauranteSalvo = restauranteRepo.save(restaurante);
		Cardapio cardapio = new Cardapio();
		cardapio.setRestaurante(restauranteSalvo);
		cardapioRepo.save(cardapio);
		return restauranteSalvo;
	}

	@PutMapping("/parceiros/restaurantes/{id}")
	Restaurante atualiza(@RequestBody Restaurante restaurante) {
		Restaurante doBD = restauranteRepo.getOne(restaurante.getId());
		restaurante.setUser(doBD.getUser());
		restaurante.setAprovado(doBD.getAprovado());
		
		Restaurante restauranteAtualizado = restauranteRepo.save(restaurante);

		if (restaurante.getAprovado() &&
				( !restaurante.getCep().equals(doBD.getCep())
						||
				  !restaurante.getTipoDeCozinha().equals(doBD.getTipoDeCozinha())
				)
				) {
			distanciaRestClient.restauranteAtualizado(restauranteAtualizado);
		}
		
		return restauranteAtualizado;
	}

	@GetMapping("/admin/restaurantes/em-aprovacao")
	List<RestauranteDto> emAprovacao() {
		return restauranteRepo.findAllByAprovado(false).stream().map(RestauranteDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	@PatchMapping("/admin/restaurantes/{id}")
	public void aprova(@PathVariable("id") Long id) {
		restauranteRepo.aprovaPorId(id);
		Restaurante restauranteAprovado = restauranteRepo.getOne(id);
		distanciaRestClient.novoRestauranteAprovado(restauranteAprovado);
	}
}
