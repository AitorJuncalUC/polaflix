package es.unican.aitor.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Serie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSerie;
	private String titulo;
	private String sinopsis;
	
	@Embedded
	private Categoria categoria;
	
	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	@OrderBy("numero")
	private Set<Temporada> temporadas;
	
	@ElementCollection
	private ArrayList<String> actores;
	
	@ElementCollection
	private ArrayList<String> autores;
	
	
	protected Serie() {
		
	}
	
	public Serie(String titulo, String sinopsis, Categoria categoria, HashSet<Temporada> temporadas,
			ArrayList<String> actores, ArrayList<String> autores) {
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.categoria = categoria;
		this.temporadas = temporadas;
		this.actores = actores;
		this.autores = autores;
	}

	public ArrayList<String> getActores() {
		return actores;
	}

	public void setActores(ArrayList<String> actores) {
		this.actores = actores;
	}

	public ArrayList<String> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}

	public Set<Temporada> getTemporadas() {
		return temporadas;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	public void anhadeTemporada(Temporada temporada) {
		temporadas.add(temporada);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSerie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		return idSerie == other.idSerie;
	}
}
