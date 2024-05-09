package es.unican.aitor.polaflix.dominio;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Temporada {
	@JsonView({Views.SerieView.class, Views.CapituloVistoView.class})
	private int numero;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({Views.SerieView.class})
	private Long id;
	
	@ManyToOne
	@JsonBackReference 
	@JsonView({Views.CapituloVistoView.class})
	private Serie serie;
	
	@OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
	@OrderBy("numero")
	@JsonView({Views.SerieView.class})
	private List<Capitulo> capitulos;
	
	
	protected Temporada() {
		
	}
	
	public Temporada(int numero, Serie serie, List<Capitulo> capitulos) {
		this.numero = numero;
		this.serie = serie;
		this.capitulos = capitulos;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void anhadeCapitulo(Capitulo c) {
		capitulos.add(c);
	}

	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Temporada other = (Temporada) obj;
		return id == other.id;
	}
	
	
	
}
