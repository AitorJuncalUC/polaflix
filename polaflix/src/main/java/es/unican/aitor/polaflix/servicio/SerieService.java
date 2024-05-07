package es.unican.aitor.polaflix.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.aitor.polaflix.dominio.Serie;
import es.unican.aitor.polaflix.repositorios.SerieRepository;
import jakarta.transaction.Transactional;

@Service
public class SerieService {
	
	@Autowired
	private SerieRepository sr;
	
	public List<Serie> getSeries() {
		List<Serie> series = sr.findAll();
		return series;
	}
	
	public Optional<Serie> getSerieById(int id) {
		Optional<Serie> serie = sr.findById(id);
		return serie;
	}
	
	public Serie getSerieByTitulo(String titulo) {
		Serie serie = sr.findByTitulo(titulo);
		return serie;
	}
	
	public List<Serie> getSeriesByLetraInicial(String letra) {
		List<Serie> seriesTodas = getSeries();
		List<Serie> series = new ArrayList<Serie>();
		for(Serie serie : seriesTodas) {
			if(serie.getTitulo().charAt(0)==letra.charAt(0)) {
				series.add(serie);
			}
		}
		return series;
	}
}
