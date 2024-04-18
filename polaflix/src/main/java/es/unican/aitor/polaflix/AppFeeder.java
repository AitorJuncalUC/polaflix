package es.unican.aitor.polaflix;


import java.util.ArrayList;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.unican.aitor.polaflix.dominio.*;
import es.unican.aitor.polaflix.repositorios.*;

@Component
public class AppFeeder implements CommandLineRunner {
	
	@Autowired
	protected UsuarioRepository ur;
	@Autowired
	protected SerieRepository sr;

	
	@Override
	public void run(String... args) throws Exception {
		feedUsuarios();
		feedSeries();
		
		testUsuario();
		
		System.out.println("Application feeded");
	}

	private void feedUsuarios() {
		//Usuario Estandar
		Usuario u1 = new Usuario("Paco","1234", "iban", false);
		//Usuario Premium
		Usuario u2 = new Usuario("Lola","12345", "iban2", true);
		
		//Creacion facturas
		Date date = new Date();
		Cargo c11 = new Cargo(date, null);
		Cargo c12 = new Cargo(date, null);
		ArrayList<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(c11); cargos.add(c12);
		Factura f1 = new Factura(date, cargos, u1);
		u1.anhadeFactura(f1);
		
		date = new Date();
		Cargo c21 = new Cargo(date, null);
		Cargo c22 = new Cargo(date, null);
		cargos = new ArrayList<Cargo>();
		cargos.add(c21); cargos.add(c22);
		Factura f2 = new Factura(date, cargos, u1);
		u1.anhadeFactura(f2);
		
		//Guarda usuarios
		ur.save(u1);
		ur.save(u2);
	}
	
	private void feedSeries() {
		Serie s1 = null, s2 = null;
		Temporada t11 = null, t12 = null, t21 = null, t22 = null;
		Capitulo c11 = null, c12 = null;
		
		c11 = new Capitulo("c11", 1, "capitulo 1 de la temporada 1", t11);
		c12 = new Capitulo("c11", 2, "capitulo 2 de la temporada 1", t11);
		Set<Capitulo> capitulos11 = new HashSet<Capitulo>();
		capitulos11.add(c11); capitulos11.add(c12);
		c11 = new Capitulo("c11", 1, "capitulo 1 de la temporada 2", t12);
		c12 = new Capitulo("c11", 2, "capitulo 2 de la temporada 2", t12);
		Set<Capitulo> capitulos12 = new HashSet<Capitulo>();
		capitulos12.add(c11); capitulos12.add(c12);
		
		c11 = new Capitulo("c11", 1, "capitulo 1 de la temporada 1", t21);
		c12 = new Capitulo("c11", 2, "capitulo 2 de la temporada 1", t21);
		Set<Capitulo> capitulos21 = new HashSet<Capitulo>();
		capitulos21.add(c11); capitulos21.add(c12);
		c11 = new Capitulo("c11", 1, "capitulo 1 de la temporada 2", t22);
		c12 = new Capitulo("c11", 2, "capitulo 2 de la temporada 2", t22);
		Set<Capitulo> capitulos22 = new HashSet<Capitulo>();
		capitulos22.add(c11); capitulos22.add(c12);
		
		t11 = new Temporada(1, s1, capitulos11);
		t12 = new Temporada(2, s1, capitulos12);
		t11 = new Temporada(1, s2, capitulos21);
		t12 = new Temporada(2, s2, capitulos22);
		Set<Temporada> temporadas1 = new HashSet<Temporada>();
		temporadas1.add(t11); temporadas1.add(t12);
		Set<Temporada> temporadas2 = new HashSet<Temporada>();
		temporadas1.add(t21); temporadas1.add(t22);
		
		ArrayList<String> autores = new ArrayList<String>();
		autores.add("pepe"); autores.add("luis");
		ArrayList<String> actores = new ArrayList<String>();
		actores.add("paco"); actores.add("jose");
		
		s1 = new Serie("serie_1", "sinopsis de serie 1", Categoria.GOLD, temporadas1, actores, autores);
		s2 = new Serie("serie_2", "sinopsis de serie 2", Categoria.SILVER, temporadas2, actores, autores);
		
		sr.save(s1);
		sr.save(s2);
	}

	
	private void testUsuario() {
		
	}

}
