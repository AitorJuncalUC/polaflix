package es.unican.aitor.polaflix.spring;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class CapituloID {
	@Id
	private int numero;
	@Id
	private int numTemporada;
	@Id
	private int idSerie;
	
	public CapituloID(int numero, int numTemporada, int idSerie) {
		this.numero = numero;
		this.numTemporada = numTemporada;
		this.idSerie = idSerie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumTemporada() {
		return numTemporada;
	}

	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}

	public int getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(int idSerie) {
		this.idSerie = idSerie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSerie, numTemporada, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapituloID other = (CapituloID) obj;
		return idSerie == other.idSerie && numTemporada == other.numTemporada && numero == other.numero;
	}
	
	
}
