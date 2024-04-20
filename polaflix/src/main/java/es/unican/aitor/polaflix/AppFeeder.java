package es.unican.aitor.polaflix;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import es.unican.aitor.polaflix.dominio.*;
import es.unican.aitor.polaflix.repositorios.*;
import jakarta.transaction.Transactional;

@Component
public class AppFeeder implements CommandLineRunner {
	
	@Autowired
	protected UsuarioRepository ur;
	@Autowired
	protected SerieRepository sr;

	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		feedSeries();
		feedUsuarios();
		testUsuario();
		
		System.out.println("Application feeded");
	}

	private void feedUsuarios() {
		//Usuario Estandar
		Usuario u1 = new Usuario("Paco","1234", "iban", false);
		//Usuario Premium
		Usuario u2 = new Usuario("Lola","12345", "iban2", true);
		
		//ver capitulos
		Date ahora = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(ahora);
		int anhoActual = calendario.get(Calendar.YEAR);
		int mesActual = calendario.get(Calendar.MONTH);
		
		List<Serie> series = sr.findAll();
		Serie s1 = series.get(0);
		Serie s2 = series.get(1);
		Capitulo c1 = s1.getTemporadas().get(0).getCapitulos().get(0);
		Capitulo c2 = s2.getTemporadas().get(0).getCapitulos().get(0);
		
		u1.anhadeSerie(s1); u1.anhadeSerie(s2);
		u1.comenzarSerie(s1); u1.comenzarSerie(s2);
		u1.verCapitulo(c1); u1.verCapitulo(c2);
		double importe_u1 = u1.verFactura(anhoActual, mesActual);
		
		u2.anhadeSerie(s1); u2.anhadeSerie(s2);
		u2.comenzarSerie(s1);
		u2.verCapitulo(c1);
		double importe_u2 = u2.verFactura(anhoActual, mesActual);
		
		
		//Guarda usuarios
		ur.save(u1);
		ur.save(u2);
	}
	
	private void feedSeries() {
		Serie s1;
		List<Temporada> temporadas1 = new ArrayList<Temporada>();
		ArrayList<String> actores = new ArrayList<String>();
		ArrayList<String> autores = new ArrayList<String>();
		autores.add("Mat Duffer"); autores.add("Ross Duffer");
		actores.add("Millie Bobby Brown"); actores.add("David Harbour"); actores.add("Gaten Matarazzo");
		s1 = new Serie("Stranger things", "sigue las aventuras de un grupo de amigos en la década de 1980 mientras enfrentan "
				+ "misterios sobrenaturales, incluyendo la desaparición de uno de ellos "
				+ "y el descubrimiento de un mundo paralelo llamado el Mundo del Revés.", Categoria.ESTANDAR, 
				temporadas1, actores, autores);
		
		Serie s2;
		List<Temporada> temporadas2 = new ArrayList<Temporada>();
		actores = new ArrayList<String>();
		autores = new ArrayList<String>();
		autores.add("Steven Knight");
		actores.add("Cillian Murphy"); actores.add("Paul Anderson"); actores.add("Joe Cole");
		s2 = new Serie("Peaky blinders", "sigue a la familia Shelby, encabezada por el carismático "
				+ "gánster Thomas Shelby, mientras ascienden en el mundo criminal de Birmingham "
				+ "después de la Primera Guerra Mundial.", Categoria.SILVER, 
				temporadas2, actores, autores);
		
		Temporada t11, t12;
		Temporada t21, t22;
		List<Capitulo> capitulos11 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos12 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos21 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos22 = new ArrayList<Capitulo>();
		t11 = new Temporada(1, s1, capitulos11);
		t12 = new Temporada(2, s1, capitulos12);
		t21 = new Temporada(1, s2, capitulos21);
		t22 = new Temporada(2, s2, capitulos22);
		s1.anhadeTemporada(t11); s1.anhadeTemporada(t12);
		s2.anhadeTemporada(t21); s2.anhadeTemporada(t22);
		
		Capitulo c111 = new Capitulo("El desaparecido de Will Byers" , 1, "Will Byers desaparece "
				+ "misteriosamente, y su madre y amigos comienzan a buscarlo, encontrándose con una niña con "
				+ "poderes telequinéticos.", t11);
		Capitulo c112 = new Capitulo("La pulga y el acróbata" , 3, "Los amigos de Will encuentran pistas "
				+ "sobre su paradero mientras Eleven revela sus poderes al grupo.", t11);
		Capitulo c113 = new Capitulo("El Mundo del Revés" , 8, "Los secretos del laboratorio se revelan "
				+ "mientras el grupo enfrenta a criaturas aterradoras en el Mundo del Revés.", t11);
		Capitulo c121 = new Capitulo("MadMax" , 1, "Un nuevo personaje, Max, llega a Hawkins mientras la "
				+ "ciudad se enfrenta a nuevas amenazas y misterios.", t12);
		Capitulo c122 = new Capitulo("Dig Dug" , 5, " Will experimenta visiones mientras Joyce y Hopper "
				+ "descubren más sobre el Mundo del Revés.", t12);
		Capitulo c123 = new Capitulo("El portal" , 9, " El grupo se une para enfrentar al Demogorgon "
				+ "y cerrar el portal hacia el Mundo del Revés.", t12);	
		
		Capitulo c211 = new Capitulo("Episodio 1" , 1, "La familia Shelby planea un robo y se cruza con "
				+ "una agente infiltrada.", t21);
		Capitulo c212 = new Capitulo("Episodio 3" , 3, "Thomas Shelby se enfrenta a nuevos enemigos mientras "
				+ "su hermana Polly es liberada de prisión.", t21);
		Capitulo c213 = new Capitulo("Episodio 6" , 6, "La rivalidad con la familia Lee alcanza su punto"
				+ " álgido mientras los Shelby se preparan para la guerra.", t21);
		Capitulo c221 = new Capitulo("Episodio 1" , 1, "Los Shelby enfrentan las consecuencias de sus acciones"
				+ " mientras Thomas intenta expandir su imperio.", t22);
		Capitulo c222 = new Capitulo("Episodio 3" , 3, "La familia enfrenta nuevas amenazas mientras el "
				+ "gobierno y la policía se acercan a los Peaky Blinders.", t22);
		Capitulo c223 = new Capitulo("Episodio 6" , 6, "Thomas planea su venganza contra Campbell mientras "
				+ "la familia se ve amenazada por un nuevo enemigo.", t22);	
		
		t11.anhadeCapitulo(c111); t11.anhadeCapitulo(c112); t11.anhadeCapitulo(c113);
		t12.anhadeCapitulo(c121); t12.anhadeCapitulo(c122); t12.anhadeCapitulo(c123);
		t21.anhadeCapitulo(c211); t21.anhadeCapitulo(c212); t21.anhadeCapitulo(c213);
		t22.anhadeCapitulo(c221); t22.anhadeCapitulo(c222); t21.anhadeCapitulo(c223);
		
		sr.save(s1);
		sr.save(s2);
	}

	
	private void testUsuario() {
		
	}

}
