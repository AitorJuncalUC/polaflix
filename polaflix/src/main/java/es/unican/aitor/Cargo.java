package es.unican.aitor;

import java.util.Date;
import java.util.Objects;

public class Cargo {
	private int idCargo;
	private Date fecha;
	private double precio;
	private Capitulo capitulo;
	
	public Cargo(Date fecha, Capitulo capitulo) {
		this.fecha = fecha;
		this.capitulo = capitulo;
		Categoria c = capitulo.getTemporada().getSerie().getCategoria();
		switch(c) {
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

	public Capitulo getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idCargo);
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
		return idCargo == other.idCargo;
	}
}
