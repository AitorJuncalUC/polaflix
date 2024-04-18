package es.unican.aitor.polaflix.pojos;

import java.util.Objects;
import java.util.Set;

public class Temporada {
	private int numero;
	private Serie serie;
	private Set<Capitulo> capitulos;

	public Temporada(int numero, Serie serie, Set<Capitulo> capitulos) {
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

	public void setCapitulos(Set<Capitulo> capitulos) {
		this.capitulos = capitulos;
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

	public Set<Capitulo> getCapitulos() {
		return capitulos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero, serie);
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
		return numero == other.numero && Objects.equals(serie, other.serie);
	}
	
	
	
}
