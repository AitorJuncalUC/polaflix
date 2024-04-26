package es.unican.aitor.polaflix.dominio;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Factura {
	private Date fecha;
	private double importeTotal;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFactura;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("fecha")
	private List<Cargo> cargos;
	
	@ManyToOne
	private Usuario usuario;
	
	protected Factura() {
		
	}
	
	public Factura(Date fecha, List<Cargo> cargos, Usuario usuario) {
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

	public List<Cargo> getCargos() {
		return cargos;
	}
	
	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
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
		if (premium) { this.importeTotal = 20.0 ; return this.importeTotal; }
		for(Cargo c : cargos) {
			importeTotal += c.getPrecio();
		}
		return importeTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idFactura);
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
		return idFactura == other.idFactura;
	}
}
