package es.unican.aitor.polaflix.spring;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class TemporadaID {
	@Id
	private int numero;
	@Id
	private int idSerie;
	
	public TemporadaID(int numero, int idSerie) {
		this.numero = numero;
		this.idSerie = idSerie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSerie, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemporadaID other = (TemporadaID) obj;
		return idSerie == other.idSerie && numero == other.numero;
	}
}
