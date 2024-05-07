package es.unican.aitor.polaflix.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unican.aitor.polaflix.dominio.Factura;
import es.unican.aitor.polaflix.dominio.Serie;
import es.unican.aitor.polaflix.dominio.Usuario;
import es.unican.aitor.polaflix.repositorios.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository urep;
	
	@GetMapping("/{nombre}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String nombre) {
		Usuario usuario = urep.findByNombre(nombre);
		if(usuario == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/{nombre}/facturas")
    public ResponseEntity<List<Factura>> getFacturas(@PathVariable String nombre, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
		Usuario usuario = urep.findByNombre(nombre);
		List<Factura> facturas = new ArrayList<Factura>();
        if(fecha == null) {
        	facturas = usuario.getFacturas(); 
        }
        else {
        	Calendar calendario = Calendar.getInstance();
        	calendario.setTime(fecha);
        	Factura f = usuario.verFactura(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH));
        	facturas.add(f);
        }
        if (facturas == null) {
        	return ResponseEntity.badRequest().build();
        }
        else {
        	return ResponseEntity.ok(facturas);
        }
    }
	
	@PutMapping("/{nombre}/seriesPendientes/{id}")
	public ResponseEntity<List<Serie>> addSeriePendiente(@PathVariable("nombre") String nombre, @PathVariable("id") int id) {
		return null;
	}
}
