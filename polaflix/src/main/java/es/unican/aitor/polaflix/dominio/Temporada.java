package es.unican.aitor.polaflix.dominio;

import java.util.Objects;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Temporada {
	private int numero;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTemporada;
	
	@ManyToOne
	private Serie serie;
	
	@OneToMany(mappedBy = "temporada", cascade = CascadeType.ALL)
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
		return Objects.hash(idTemporada);
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
		return idTemporada == other.idTemporada;
	}
	
	
	
}
