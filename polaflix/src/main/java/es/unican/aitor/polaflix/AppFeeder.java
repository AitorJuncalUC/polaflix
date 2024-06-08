package es.unican.aitor.polaflix;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		u1.verCapitulo(c1); u1.verCapitulo(c2);
		Factura f1 = u1.verFactura(anhoActual, mesActual);
		
		u2.anhadeSerie(s1);
		u2.verCapitulo(c1);
		Factura f2 = u2.verFactura(anhoActual, mesActual);
		
		
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
		s1 = new Serie("Stranger Things", "sigue las aventuras de un grupo de amigos en la década de 1980 mientras enfrentan "
				+ "misterios sobrenaturales, incluyendo la desaparición de uno de ellos "
				+ "y el descubrimiento de un mundo paralelo llamado el Mundo del Revés.", Categoria.ESTANDAR, 
				temporadas1, actores, autores);
		
		Serie s2;
		List<Temporada> temporadas2 = new ArrayList<Temporada>();
		actores = new ArrayList<String>();
		autores = new ArrayList<String>();
		autores.add("Steven Knight");
		actores.add("Cillian Murphy"); actores.add("Paul Anderson"); actores.add("Joe Cole");
		s2 = new Serie("Peaky Blinders", "sigue a la familia Shelby, encabezada por el carismático "
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
		
		
		Serie s3;
		List<Temporada> temporadas3 = new ArrayList<Temporada>();
		ArrayList<String> actores3 = new ArrayList<String>();
		ArrayList<String> autores3 = new ArrayList<String>();
		autores3.add("David Benioff"); autores3.add("D.B. Weiss");
		actores3.add("Emilia Clarke"); actores3.add("Kit Harington"); actores3.add("Peter Dinklage");
		s3 = new Serie("Game of Thrones", "sigue las luchas de poder entre familias nobles en los Siete Reinos de Westeros, mientras amenazas sobrenaturales emergen más allá del Muro.", Categoria.GOLD, temporadas3, actores3, autores3);
		
		Temporada t31, t32;
		List<Capitulo> capitulos31 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos32 = new ArrayList<Capitulo>();
		t31 = new Temporada(1, s3, capitulos31);
		t32 = new Temporada(2, s3, capitulos32);
		s3.anhadeTemporada(t31); s3.anhadeTemporada(t32);
		
		Capitulo c311 = new Capitulo("Winter Is Coming", 1, "Los Stark de Winterfell se enfrentan a la llegada del rey Robert Baratheon y su corte, mientras extrañas criaturas aparecen más allá del Muro.", t31);
		Capitulo c312 = new Capitulo("The Kingsroad", 2, "Ned Stark acompaña al rey Robert a King's Landing, mientras Daenerys Targaryen se adapta a su nuevo papel como esposa de Khal Drogo.", t31);
		Capitulo c313 = new Capitulo("Fire and Blood", 10, "Ned Stark enfrenta su destino en King's Landing mientras Daenerys toma decisiones que cambiarán su vida para siempre.", t31);
		Capitulo c321 = new Capitulo("The North Remembers", 1, "Robb Stark continúa su guerra contra los Lannister mientras Tyrion llega a King's Landing para actuar como Mano del Rey.", t32);
		Capitulo c322 = new Capitulo("Blackwater", 9, "La batalla de Blackwater pone a prueba la defensa de King's Landing mientras Stannis Baratheon intenta tomar el trono.", t32);
		Capitulo c323 = new Capitulo("Valar Morghulis", 10, "Los personajes enfrentan las consecuencias de la batalla de Blackwater mientras nuevas alianzas se forjan.", t32);
		
		t31.anhadeCapitulo(c311); t31.anhadeCapitulo(c312); t31.anhadeCapitulo(c313);
		t32.anhadeCapitulo(c321); t32.anhadeCapitulo(c322); t32.anhadeCapitulo(c323);
		
		
		Serie s4;
		List<Temporada> temporadas4 = new ArrayList<Temporada>();
		ArrayList<String> actores4 = new ArrayList<String>();
		ArrayList<String> autores4 = new ArrayList<String>();
		autores4.add("Vince Gilligan");
		actores4.add("Bryan Cranston"); actores4.add("Aaron Paul"); actores4.add("Anna Gunn");
		s4 = new Serie("Breaking Bad", "sigue la transformación de Walter White, un profesor de química que se convierte en fabricante de metanfetaminas, mientras enfrenta a diversos enemigos en el mundo del narcotráfico.", Categoria.SILVER, temporadas4, actores4, autores4);

		Temporada t41, t42;
		List<Capitulo> capitulos41 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos42 = new ArrayList<Capitulo>();
		t41 = new Temporada(1, s4, capitulos41);
		t42 = new Temporada(2, s4, capitulos42);
		s4.anhadeTemporada(t41); s4.anhadeTemporada(t42);
		
		Capitulo c411 = new Capitulo("Pilot", 1, "Walter White descubre que tiene cáncer terminal y decide fabricar metanfetaminas para asegurar el futuro de su familia.", t41);
		Capitulo c412 = new Capitulo("Crazy Handful of Nothin'", 6, "Walter comienza a construir su imperio mientras enfrenta a rivales peligrosos en el negocio de las drogas.", t41);
		Capitulo c413 = new Capitulo("A No-Rough-Stuff-Type Deal", 7, "Walter y Jesse enfrentan nuevos desafíos mientras intentan expandir su operación de metanfetaminas.", t41);
		Capitulo c421 = new Capitulo("Seven Thirty-Seven", 1, "Walter y Jesse enfrentan las consecuencias de su éxito mientras los enemigos se cierran.", t42);
		Capitulo c422 = new Capitulo("Phoenix", 12, "Walter se enfrenta a decisiones difíciles que afectan a su familia y a su negocio.", t42);
		Capitulo c423 = new Capitulo("ABQ", 13, "La vida de Walter se complica aún más mientras intenta mantener el control de su imperio.", t42);
		
		t41.anhadeCapitulo(c411); t41.anhadeCapitulo(c412); t41.anhadeCapitulo(c413);
		t42.anhadeCapitulo(c421); t42.anhadeCapitulo(c422); t42.anhadeCapitulo(c423);
		
		
		Serie s5;
		List<Temporada> temporadas5 = new ArrayList<Temporada>();
		ArrayList<String> actores5 = new ArrayList<String>();
		ArrayList<String> autores5 = new ArrayList<String>();
		autores5.add("Jon Favreau");
		actores5.add("Pedro Pascal"); actores5.add("Gina Carano"); actores5.add("Carl Weathers");
		s5 = new Serie("The Mandalorian", "sigue las aventuras de un solitario cazarrecompensas en los confines de la galaxia, protegiendo a un misterioso niño con habilidades especiales.", Categoria.ESTANDAR, temporadas5, actores5, autores5);

		Temporada t51, t52;
		List<Capitulo> capitulos51 = new ArrayList<Capitulo>();
		List<Capitulo> capitulos52 = new ArrayList<Capitulo>();
		t51 = new Temporada(1, s5, capitulos51);
		t52 = new Temporada(2, s5, capitulos52);
		s5.anhadeTemporada(t51); s5.anhadeTemporada(t52);

		Capitulo c511 = new Capitulo("Chapter 1: The Mandalorian", 1, "Un solitario cazarrecompensas recibe una misión para capturar a un objetivo misterioso.", t51);
		Capitulo c512 = new Capitulo("Chapter 3: The Sin", 3, "El Mandalorianio enfrenta un dilema moral mientras decide el destino del niño.", t51);
		Capitulo c513 = new Capitulo("Chapter 8: Redemption", 8, "El Mandalorianio y sus aliados enfrentan una batalla final para proteger al niño.", t51);
		Capitulo c521 = new Capitulo("Chapter 9: The Marshal", 1, "El Mandalorianio busca a otros de su especie para ayudarle a encontrar el hogar del niño.", t52);
		Capitulo c522 = new Capitulo("Chapter 13: The Jedi", 5, "El Mandalorianio se encuentra con una antigua Jedi que puede ayudarle en su misión.", t52);
		Capitulo c523 = new Capitulo("Chapter 16: The Rescue", 8, "El Mandalorianio y sus aliados lanzan una misión de rescate desesperada.", t52);

		t51.anhadeCapitulo(c511); t51.anhadeCapitulo(c512); t51.anhadeCapitulo(c513);
		t52.anhadeCapitulo(c521); t52.anhadeCapitulo(c522); t52.anhadeCapitulo(c523);

		
		
		sr.save(s1);
		sr.save(s2);
		sr.save(s3);
		sr.save(s4);
		sr.save(s5);
	}
}
