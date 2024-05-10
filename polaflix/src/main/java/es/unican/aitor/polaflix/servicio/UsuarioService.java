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
import es.unican.aitor.polaflix.dominio.Temporada;
import es.unican.aitor.polaflix.dominio.Usuario;
import es.unican.aitor.polaflix.repositorios.SerieRepository;
import es.unican.aitor.polaflix.repositorios.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private SerieRepository sr;
	
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
	
	public Map<Integer,CapitulosVistos> getCapitulosVistos(String nombre) {
		Usuario usuario = getUsuarioByNombre(nombre);
		System.out.println(usuario.getCapitulosVistos().values());
		Map<Integer,CapitulosVistos> capitulos = usuario.getCapitulosVistos();
		return capitulos;
	}
	
	
	public void addSeriePendiente(String nombre, String nombreSerie){
		Usuario usuario = getUsuarioByNombre(nombre);
		Serie s = sr.findByTitulo(nombreSerie);
		usuario.anhadeSerie(s);
	}

	public void addCapituloVisto(String nombre, String nombreSerie, int numTemporada, int numCapitulo) {
		Usuario u = getUsuarioByNombre(nombre);
		Serie s = sr.findByTitulo(nombreSerie);
		List<Temporada> temporadas = s.getTemporadas();
		Temporada t = null;
		for(Temporada temporada : temporadas) {
			if(temporada.getNumero() == numTemporada) {
				t = temporada;
			}
		}
		Capitulo c = null;
		List<Capitulo> capitulos = t.getCapitulos();
		for(Capitulo capitulo : capitulos) {
			if(capitulo.getNumero() == numCapitulo) {
				c = capitulo;
			}
		}
		u.verCapitulo(c);
	}
}