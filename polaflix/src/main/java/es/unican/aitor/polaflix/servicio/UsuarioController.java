package es.unican.aitor.polaflix.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unican.aitor.polaflix.dominio.Capitulo;
import es.unican.aitor.polaflix.dominio.Factura;
import es.unican.aitor.polaflix.dominio.Serie;
import es.unican.aitor.polaflix.dominio.Usuario;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService us;
	
	@GetMapping("/{nombre}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String nombre) {
		Usuario usuario = us.getUsuarioByNombre(nombre);
		if(usuario == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/{nombre}/facturas")
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
	public ResponseEntity<List<Capitulo>> getCapitulosVistos(@PathVariable String nombre) {
		List<Capitulo> capitulos = us.getCapitulosVistos(nombre);
		if (capitulos == null) {
			return ResponseEntity.badRequest().build();
        }
        else {
        	return ResponseEntity.ok(capitulos);
        }
	}
	
	@PutMapping("/{nombre}/capitulosVistos")
	public ResponseEntity<Usuario> verCapitulo(@PathVariable String nombre, 
																		@PathVariable Capitulo capitulo) {
		us.addCapituloVisto(nombre, capitulo);
		if (capitulo == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(getUsuario(nombre).getBody());
        }
	}
	
	@PutMapping("/{nombre}/seriesEmpezadas")
	public ResponseEntity<Usuario> anhadeSerie(@PathVariable String nombre, 
																		@PathVariable Serie serie) {
		us.addSeriePendiente(nombre, serie);
		if (serie == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(getUsuario(nombre).getBody());
        }
	}
}
