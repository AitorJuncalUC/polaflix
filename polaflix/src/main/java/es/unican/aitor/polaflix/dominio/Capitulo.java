package es.unican.aitor.polaflix.dominio;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Capitulo {
	@JsonView({Views.SerieView.class, Views.CapituloVistoView.class})
	private String titulo;
	@JsonView({Views.SerieView.class, Views.CapituloVistoView.class})
	private int numero;
	@JsonView({Views.CapituloVistoView.class})
	private int numTemporada;
	@JsonView({Views.CapituloVistoView.class})
	private String tituloSerie;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonView({Views.SerieView.class})
	private String descripcion;
	
	@ManyToOne
	@JsonBackReference
	private Temporada temporada;
	
	protected Capitulo() {
		
	}
	
	public Capitulo(String titulo, int numero, String descripcion, Temporada temporada) {
		this.titulo = titulo;
		this.numero = numero;
		this.descripcion = descripcion;
		this.temporada = temporada;
		this.numTemporada = temporada.getNumero();
		this.tituloSerie = temporada.getSerie().getTitulo();
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumTemporada() {
		return numTemporada;
	}

	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
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
		Capitulo other = (Capitulo) obj;
		return id == other.id;
	}
}
