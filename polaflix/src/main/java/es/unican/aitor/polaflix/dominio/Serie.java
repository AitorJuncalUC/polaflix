package es.unican.aitor.polaflix.dominio;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


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
	private List<Temporada> temporadas;
	
	@ElementCollection
	private List<String> actores;
	
	@ElementCollection
	private List<String> autores;
	
	
	protected Serie() {
		
	}
	
	public Serie(String titulo, String sinopsis, Categoria categoria, List<Temporada> temporadas,
			List<String> actores, List<String> autores) {
		this.titulo = titulo;
		this.sinopsis = sinopsis;
		this.categoria = categoria;
		this.temporadas = temporadas;
		this.actores = actores;
		this.autores = autores;
	}

	public List<String> getActores() {
		return actores;
	}

	public void setActores(List<String> actores) {
		this.actores = actores;
	}

	public List<String> getAutores() {
		return autores;
	}

	public void setAutores(List<String> autores) {
		this.autores = autores;
	}

	public List<Temporada> getTemporadas() {
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
