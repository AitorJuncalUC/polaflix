package es.unican.aitor.polaflix.dominio;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import es.unican.aitor.polaflix.servicio.Views;

import java.util.ArrayList;
import java.util.Calendar;

import jakarta.persistence.*;

@Entity
public class Usuario {
	@Id
	@JsonView({Views.UsuarioView.class})
	private String nombre;
	
	@JsonView({Views.UsuarioView.class})
	private String contrasenha;
	@JsonView({Views.UsuarioView.class})
	private String IBAN;
	@JsonView({Views.UsuarioView.class})
	private boolean premium;
	
	@ManyToMany()
	@JsonView({Views.UsuarioView.class})
	private List<Serie> seriesPendientes;
	
	@ManyToMany()
	@JsonView({Views.UsuarioView.class})
	private List<Serie> seriesEmpezadas;
	
	@ManyToMany()
	@JsonView({Views.UsuarioView.class})
	private List<Serie> seriesTerminadas;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	@OrderBy("fecha")
	@JsonView({Views.FacturaView.class})
	private List<Factura> facturas;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	@JsonView({Views.CapituloVistoView.class})
	private Map<Integer,CapitulosVistos> capitulosVistos;
	
	
	protected Usuario() {
		
	}
	
	public Usuario(String nombre, String contrasenha, String iBAN, boolean premium) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
		this.IBAN = iBAN;
		this.premium = premium;
		this.seriesPendientes = new ArrayList<Serie>();
		this.seriesEmpezadas = new ArrayList<Serie>();
		this.seriesTerminadas = new ArrayList<Serie>();
		this.facturas = new ArrayList<Factura>();
		this.capitulosVistos = new HashMap<Integer,CapitulosVistos>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		this.IBAN = iBAN;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	public List<Serie> getSeriesPendientes() {
		return seriesPendientes;
	}

	public List<Serie> getSeriesEmpezadas() {
		return seriesEmpezadas;
	}

	public List<Serie> getSeriesTerminadas() {
		return seriesTerminadas;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}
	
	public void anhadeFactura(Factura f) {
		facturas.add(f);
	}

	public Map<Integer,CapitulosVistos> getCapitulosVistos() {
		return capitulosVistos;
	}
	
	public Factura verFactura(int anho, int mes) {
		Factura factura = null;
		Calendar calendario = Calendar.getInstance();
		int anhoFactura;
		int mesFactura;
		for(Factura f : facturas) {
			calendario.setTime(f.getFecha());
			anhoFactura = calendario.get(Calendar.YEAR);
			mesFactura = calendario.get(Calendar.MONTH);
			if(anhoFactura == anho && mesFactura == mes) {
				factura = f;
				break;
			}
		}
		return factura;
	}
	
	
	public void anhadeSerie(Serie s) {
		if(!seriesPendientes.contains(s) && s != null && !seriesEmpezadas.contains(s)) {
			seriesPendientes.add(s);
		}
	}
	public void comenzarSerie(Serie s) {
		if(!seriesPendientes.contains(s) && !seriesEmpezadas.contains(s)) {
			anhadeSerie(s);
		}
		if(seriesPendientes.contains(s) && s != null){
			seriesPendientes.remove(s);
			seriesEmpezadas.add(s);
			capitulosVistos.put(s.getId(), new CapitulosVistos(this));
		}
	}
	
	public void terminarSerie(Serie s) {
		if(!seriesTerminadas.contains(s) && seriesEmpezadas.contains(s) && s != null) {
			seriesEmpezadas.remove(s);
			seriesTerminadas.add(s);
		}
	}
	
	public void verCapitulo(Capitulo c) {
		Serie serie = c.getTemporada().getSerie();
		comenzarSerie(serie);
		if(seriesTerminadas.contains(serie)) {
			return;
		}
		Categoria cat = serie.getCategoria();
		capitulosVistos.get(serie.getId()).anhadeCapitulo(c);
		
		Date ahora = new Date();
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(ahora);
		int anhoActual = calendario.get(Calendar.YEAR);
		int mesActual = calendario.get(Calendar.MONTH);
		if(facturas.size() > 0) {
			Factura ultimaFactura = facturas.get(facturas.size()-1);
			calendario.setTime(ultimaFactura.getFecha());
			int anhoFactura = calendario.get(Calendar.YEAR);
			int mesFactura = calendario.get(Calendar.MONTH);
			
			Cargo cargo = new Cargo(ahora, serie.getTitulo(), c.getTemporada().getNumero(), c.getNumero(), cat);
			if(anhoFactura != anhoActual || mesFactura != mesActual) {
				Factura factura = new Factura(ahora, this);
				factura.anhadeCargo(cargo);
				facturas.add(factura);
			}
			else {
				ultimaFactura.anhadeCargo(cargo);
			}
		}
		else {
			Cargo cargo = new Cargo(ahora, serie.getTitulo(), c.getTemporada().getNumero(), c.getNumero(), cat);
			Factura factura = new Factura(ahora, this);
			factura.anhadeCargo(cargo);
			facturas.add(factura);
		}
		
		int capitulosTotales = 0;
		for(Temporada temporada : serie.getTemporadas()) {
			capitulosTotales += temporada.getCapitulos().size();
		}
		List<Capitulo> capitulos = capitulosVistos.get(serie.getId()).getCapitulos();
		if(capitulosTotales == capitulos.size()) {
			terminarSerie(serie);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombre, other.nombre);
	}
}
