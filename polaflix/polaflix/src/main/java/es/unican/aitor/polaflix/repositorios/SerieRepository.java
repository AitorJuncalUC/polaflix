package es.unican.aitor.polaflix.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.aitor.polaflix.dominio.Serie;


public interface SerieRepository extends JpaRepository<Serie, Integer>{
	public Serie findByTitulo(String nombre);
}

