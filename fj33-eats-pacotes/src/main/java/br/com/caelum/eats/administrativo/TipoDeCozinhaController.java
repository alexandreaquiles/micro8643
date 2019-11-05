package br.com.caelum.eats.administrativo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
class TipoDeCozinhaController {

	private TipoDeCozinhaRepository repo;

	@GetMapping("/tipos-de-cozinha")
	List<TipoDeCozinhaDto> lista() {
		return repo.findAllByOrderByNomeAsc().stream().map(TipoDeCozinhaDto::new).collect(Collectors.toList());
	}

	@PostMapping("/admin/tipos-de-cozinha")
	TipoDeCozinhaDto adiciona(@RequestBody TipoDeCozinhaDto tipoDeCozinhaDto) {
		return new TipoDeCozinhaDto(repo.save(tipoDeCozinhaDto.toTipoDeCozinha()));
	}

	@PutMapping("/admin/tipos-de-cozinha/{id}")
	TipoDeCozinhaDto atualiza(@RequestBody TipoDeCozinhaDto tipoDeCozinhaDto) {
		return new TipoDeCozinhaDto(repo.save(tipoDeCozinhaDto.toTipoDeCozinha()));
	}

	@DeleteMapping("/admin/tipos-de-cozinha/{id}")
	void remove(@PathVariable("id") Long id) {
		repo.deleteById(id);
	}

}
