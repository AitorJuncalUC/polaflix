package es.unican.aitor.polaflix.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;

@Entity
public class Factura {
	@JsonView({Views.FacturaView.class})
	private Date fecha;
	@JsonView({Views.FacturaView.class})
	private double importeTotal;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ElementCollection()
	@JsonView({Views.FacturaView.class})
	private List<Cargo> cargos;
	
	
	
	@ManyToOne
	@JsonBackReference
	private Usuario usuario;
	
	protected Factura() {
		
	}
	
	public Factura(Date fecha, Usuario usuario) {
		this.fecha = fecha;
		this.cargos = new ArrayList<Cargo>();
		this.usuario = usuario;
		if(usuario.isPremium()) {
			this.importeTotal = 20.0;
		}
		else {
			this.importeTotal = 0.0;
		}
		
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
	
	public double getImporteTotal() {
		return importeTotal;
	}
	
	public void anhadeCargo(Cargo cargo) {
		if(usuario.isPremium()) {
			cargo.setPrecio(0.0);
		}
		this.importeTotal += cargo.getPrecio();
		cargos.add(cargo);
	}

	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
		Factura other = (Factura) obj;
		return id == other.id;
	}
}
