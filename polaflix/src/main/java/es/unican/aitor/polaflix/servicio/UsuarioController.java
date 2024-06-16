package es.unican.aitor.polaflix.servicio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.dominio.Capitulo;
import es.unican.aitor.polaflix.dominio.CapitulosVistos;
import es.unican.aitor.polaflix.dominio.Factura;
import es.unican.aitor.polaflix.dominio.Usuario;


@RestController
@CrossOrigin(origins= "http://localhost:4200", maxAge=3600)
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService us;
	
	@GetMapping("")
	@JsonView({Views.UsuarioView.class})
	public ResponseEntity<List<Usuario>> getUsuarios() {
		List<Usuario> usuarios = us.getUsuarios();
		if(usuarios == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{nombre}")
	@JsonView({Views.UsuarioView.class})
	public ResponseEntity<Usuario> getUsuario(@PathVariable String nombre) {
		Usuario usuario = us.getUsuarioByNombre(nombre);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@GetMapping("/{nombre}/facturas")
	@JsonView({Views.FacturaView.class})
    public ResponseEntity<List<Factura>> getFacturas(@PathVariable String nombre, @RequestParam(required = false) String fecha) {
		List<Factura> facturas = new ArrayList<Factura>();
        if(fecha == null) {
        	facturas = us.getFacturas(nombre);
        }
        else {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
    		Date date = null;
    		try {
    			date = dateFormat.parse(fecha);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        	Factura factura = us.getFacturaByFecha(nombre, date);
        	if(factura == null) {
        		return ResponseEntity.notFound().build();
        	}
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
	public ResponseEntity<Capitulo> verCapitulo(@PathVariable String nombre, 
			@RequestParam("idSerie") int idSerie, @RequestParam("numTemporada") int numTemporada, @RequestParam("numCapitulo") int numCapitulo) {
		Capitulo capitulo = us.addCapituloVisto(nombre, idSerie, numTemporada, numCapitulo);
		if (capitulo == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(capitulo);
        }
	}
	
	@GetMapping("/{nombre}/capitulosVistos/ultimoVisto")
	@JsonView({Views.CapituloVistoView.class})
	public ResponseEntity<Capitulo> getUltimoCapitulo(@PathVariable String nombre, @RequestParam("idSerie") int idSerie) {
		Capitulo capitulo = us.ultimoCapituloVistoSerie(nombre, idSerie);
		if (capitulo == null) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(capitulo);
        }
	}
	
	@PutMapping("/{nombre}/seriesPendientes")
	@JsonView({Views.UsuarioView.class})
	public ResponseEntity<Usuario> anhadeSerie(@PathVariable String nombre, 
			@RequestParam("idSerie") int idSerie) {
		Usuario usuario = us.getUsuarioByNombre(nombre);
		boolean correcto = us.addSeriePendiente(nombre, idSerie);
		if (!correcto) {
			return ResponseEntity.notFound().build();
        }
        else {
        	return ResponseEntity.ok(usuario);
        }
	}
}
