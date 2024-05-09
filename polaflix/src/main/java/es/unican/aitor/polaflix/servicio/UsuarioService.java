package es.unican.aitor.polaflix.servicio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.aitor.polaflix.dominio.Capitulo;
import es.unican.aitor.polaflix.dominio.CapitulosVistos;
import es.unican.aitor.polaflix.dominio.Factura;
import es.unican.aitor.polaflix.dominio.Serie;
import es.unican.aitor.polaflix.dominio.Usuario;
import es.unican.aitor.polaflix.repositorios.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = ur.findAll();
		return usuarios;
	}
	
	public Usuario getUsuarioByNombre(String nombre) {
		Usuario usuario = ur.findByNombre(nombre);
		return usuario;
	}
	
	public List<Factura> getFacturas(String nombre) {
		Usuario usuario = getUsuarioByNombre(nombre);
		List<Factura> facturas = usuario.getFacturas();
		return facturas;
	}
	
	public Factura getFacturaByFecha(String nombre, Date fecha) {
		Calendar calendario = Calendar.getInstance();
    	calendario.setTime(fecha);
    	Usuario usuario = getUsuarioByNombre(nombre);
    	Factura factura = usuario.verFactura(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH));
    	return factura;
	}
	
	public Map<Long,CapitulosVistos> getCapitulosVistos(String nombre) {
		Usuario usuario = getUsuarioByNombre(nombre);
		Map<Long,CapitulosVistos> capitulos = usuario.getCapitulosVistos();
		return capitulos;
	}
	
	public void addCapituloVisto(String nombre, Capitulo capitulo) {
		Usuario usuario = getUsuarioByNombre(nombre);
		usuario.verCapitulo(capitulo);
	}
	
	public void addSeriePendiente(String nombre, Serie serie){
		Usuario usuario = getUsuarioByNombre(nombre);
		usuario.anhadeSerie(serie);
	}
}
