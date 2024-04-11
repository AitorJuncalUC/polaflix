package es.unican.aitor.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Factura {
	private Date fecha;
	private double importeTotal;
	private ArrayList<Cargo> cargos;
	private Usuario usuario;
	
	public Factura(Date fecha, ArrayList<Cargo> cargos, Usuario usuario) {
		this.fecha = fecha;
		this.cargos = cargos;
		this.importeTotal = 0.0;
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ArrayList<Cargo> getCargos() {
		return cargos;
	}
	
	public void anhadeCargo(Cargo cargo) {
		cargos.add(cargo);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	public double calculaImporteTotal() {
		boolean premium = usuario.isPremium();
		if (premium) { return 20.0; }
		for(Cargo c : cargos) {
			importeTotal += c.getPrecio();
		}
		return importeTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(usuario, other.usuario);
	}
}
