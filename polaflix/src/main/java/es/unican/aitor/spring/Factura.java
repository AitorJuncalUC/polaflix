package es.unican.aitor.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Factura {
	private Date fecha;
	private double importeTotal;
	
	@EmbeddedId
	private FacturaID id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private ArrayList<Cargo> cargos;
	
	@ManyToOne
	private Usuario usuario;
	
	protected Factura() {
		
	}
	
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
	
	public FacturaID getId() {
		return id;
	}

	public void setId(FacturaID id) {
		this.id = id;
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
