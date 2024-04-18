package es.unican.aitor.polaflix.spring;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class FacturaID {
	@Id
    private Date fecha;

    @Id
    private String usuario;

	public FacturaID(Date fecha, String usuario) {
		this.fecha = fecha;
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
		FacturaID other = (FacturaID) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(usuario, other.usuario);
	}
}
