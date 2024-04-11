package es.unican.aitor.pojos;

import java.util.Objects;

public class Capitulo {
	private String titulo;
	private int numero;
	private String descripcion;
	private Temporada temporada;
	
	public Capitulo(String titulo, int numero, String descripcion, Temporada temporada) {
		this.titulo = titulo;
		this.numero = numero;
		this.descripcion = descripcion;
		this.temporada = temporada;
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

	@Override
	public int hashCode() {
		return Objects.hash(numero, temporada);
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
		return numero == other.numero && Objects.equals(temporada, other.temporada);
	}
}
