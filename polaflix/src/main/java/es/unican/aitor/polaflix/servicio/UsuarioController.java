package es.unican.aitor.polaflix.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.dominio.CapitulosVistos;
import es.unican.aitor.polaflix.dominio.Factura;
import es.unican.aitor.polaflix.dominio.Usuario;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService us;
	
	@GetMapping("/{nombre}")
	@JsonView({Views.UsuarioView.class})
	public ResponseEntity<Usuario> getUsuario(@PathVariable String nombre) {
		Usuario usuario = us.getUsuarioByNombre(nombre);
		if(usuario == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/{nombre}/facturas")
	@JsonView({Views.FacturaView.class})
    public ResponseEntity<List<Factura>> getFacturas(@PathVariable String nombre, @RequestParam(required = false) Date fecha) {
		List<Factura> facturas = new ArrayList<Factura>();
        if(fecha == null) {
        	facturas = us.getFacturas(nombre);
        }
        else {
        	Factura factura = us.getFacturaByFecha(nombre, fecha);
        	facturas.add(factura);
        }
        if (facturas == null) {
        	return ResponseEntity.badRequest().build();
        }
        else {
        	return ResponseEntity.ok(facturas);
        }
    }
	
	@GetMapping("/{nombre}/capitulosVistos")
	@JsonView({Views.CapituloVistoView.class})
	public ResponseEntity<Map<Integer, CapitulosVistos>> getCapitulosVistos(@PathVariable String nombre) {
		Map<Integer,CapitulosVistos> capitulos = us.getCapitulosVistos(nombre);
		if (capitulos == null) {
			return ResponseEntity.badRequest().build();
        }
        else {
        	return ResponseEntity.ok(capitulos);
        }
	}
	
	@PutMapping("/{nombre}/capitulosVistos")
	@JsonView({Views.CapituloVistoView.class})
	@Transactional
	public ResponseEntity<Map<Integer, CapitulosVistos>> verCapitulo(@PathVariable String nombre, 
			@RequestParam("nombreSerie") String nombreSerie, @RequestParam("numTemporada") int numTemporada, @RequestParam("numCapitulo") int numCapitulo) {
		us.addCapituloVisto(nombre, nombreSerie, numTemporada, numCapitulo);
		Map<Integer, CapitulosVistos> capitulos = us.getUsuarioByNombre(nombre).getCapitulosVistos();
		if (capitulos == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(capitulos);
        }
	}
	
	@PutMapping("/{nombre}/seriesPendientes")
	@JsonView({Views.UsuarioView.class})
	@Transactional
	public ResponseEntity<Usuario> anhadeSerie(@PathVariable String nombre, 
			@RequestParam("nombreSerie") String nombreSerie) {
		us.addSeriePendiente(nombre, nombreSerie);
		Usuario usuario = getUsuario(nombre).getBody();
		if (usuario == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(usuario);
        }
	}
}
