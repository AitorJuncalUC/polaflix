package es.unican.aitor.polaflix.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.unican.aitor.polaflix.dominio.Serie;
import es.unican.aitor.polaflix.repositorios.SerieRepository;

@RestController
@RequestMapping("/series")
public class SerieController {
	@Autowired
	SerieRepository srep;
	
	@GetMapping()
	public ResponseEntity<List<Serie>> getSeries() {
		List<Serie> series = srep.findAll();
		if(series == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(series);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Serie>> getSerie(@PathVariable int id) {
		Optional<Serie> serie = srep.findById(id);
		if(serie == null) {
			return  ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(serie);
	}
}
