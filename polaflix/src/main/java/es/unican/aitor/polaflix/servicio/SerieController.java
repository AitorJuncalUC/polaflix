package es.unican.aitor.polaflix.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.dominio.Serie;


@RestController
@RequestMapping("/series")
public class SerieController {
	@Autowired
	SerieService ss;
	
	@GetMapping("")
	@JsonView({Views.SerieView.class})
	public ResponseEntity<List<Serie>> getSeries(@RequestParam(required = false) String titulo,
													@RequestParam(required = false) String inicial) {
		List<Serie> series = new ArrayList<Serie>();
		if(titulo != null) {
			Serie serie = ss.getSerieByTitulo(titulo);
			if(serie != null) {
				series.add(serie);
			}
		}
		if(inicial != null) {
			series = ss.getSeriesByLetraInicial(inicial);
		}
		if(titulo == null && inicial == null) {
			series = ss.getSeries();
		}
		if(series == null) {
			return ResponseEntity.badRequest().build();
		}
		if(series.size() == 0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(series);
	}
	
	@GetMapping("/{id}")
	@JsonView({Views.SerieView.class})
	public ResponseEntity<Optional<Serie>> getSerie(@PathVariable int id) {
		Optional<Serie> serie = ss.getSerieById(id);
		if(serie.isEmpty()) {
			return  ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(serie);
	}
}
