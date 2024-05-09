package es.unican.aitor.polaflix.dominio;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cargo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonView({Views.FacturaView.class})
	private Date fecha;
	@JsonView({Views.FacturaView.class})
	private double precio;
	
	@JsonView({Views.FacturaView.class})
	private String nombreSerie;
	@JsonView({Views.FacturaView.class})
	private int numTemporada;
	@JsonView({Views.FacturaView.class})
	private int numCapitulo;
	
	
	public Cargo(Date fecha, String nombreSerie, int numTemporada, int numCapitulo, Categoria categoria) {
		super();
		this.fecha = fecha;
		this.nombreSerie = nombreSerie;
		this.numTemporada = numTemporada;
		this.numCapitulo = numCapitulo;
		switch(categoria) {
		case ESTANDAR:
			this.precio = 0.5;
			break;
		case SILVER:
			this.precio = 0.75;
			break;
		case GOLD:
			this.precio = 1.5;
			break;
		default:
			break;
	}
	}

	protected Cargo() {
		
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public int getNumTemporada() {
		return numTemporada;
	}

	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}

	public int getNumCapitulo() {
		return numCapitulo;
	}

	public void setNumCapitulo(int numCapitulo) {
		this.numCapitulo = numCapitulo;
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
		Cargo other = (Cargo) obj;
		return id == other.id;
	}
}
