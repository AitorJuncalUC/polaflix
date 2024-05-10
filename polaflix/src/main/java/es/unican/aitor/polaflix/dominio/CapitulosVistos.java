package es.unican.aitor.polaflix.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class CapitulosVistos {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	
	@ManyToOne()
	private Usuario usuario;
	
	@ManyToMany()
	@JsonView({Views.CapituloVistoView.class})
	private List<Capitulo> capitulos;
	
	
	
	protected CapitulosVistos() {
		
	}
	public CapitulosVistos(Usuario usuario) {
		this.usuario = usuario;
		capitulos = new ArrayList<Capitulo>();
	}
	
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void anhadeCapitulo(Capitulo c) {
		capitulos.add(c);
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
		CapitulosVistos other = (CapitulosVistos) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
