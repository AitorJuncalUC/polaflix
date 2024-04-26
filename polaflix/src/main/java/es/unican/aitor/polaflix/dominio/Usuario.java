package es.unican.aitor.polaflix.dominio;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


import java.util.ArrayList;
import java.util.Calendar;

import jakarta.persistence.*;

@Entity
public class Usuario {
	@Id
	private String nombre;
	
	private String contrasenha;
	private String IBAN;
	private boolean premium;
	
	@ManyToMany()
	private Set<Serie> seriesPendientes;
	
	@ManyToMany()
	private Set<Serie> seriesEmpezadas;
	
	@ManyToMany()
	private Set<Serie> seriesTerminadas;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	@OrderBy("fecha")
	private List<Factura> facturas;
	
	@ManyToMany()
	private Set<Capitulo> capitulosVistos;
	
	
	protected Usuario() {
		
	}
	
	public Usuario(String nombre, String contrasenha, String iBAN, boolean premium) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
		this.IBAN = iBAN;
		this.premium = premium;
		this.seriesPendientes = new HashSet<Serie>();
		this.seriesEmpezadas = new HashSet<Serie>();
		this.seriesTerminadas = new HashSet<Serie>();
		this.facturas = new ArrayList<Factura>();
		this.capitulosVistos = new HashSet<Capitulo>();
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
	
	public Set<Serie> getSeriesPendientes() {
		return seriesPendientes;
	}

	public Set<Serie> getSeriesEmpezadas() {
		return seriesEmpezadas;
	}

	public Set<Serie> getSeriesTerminadas() {
		return seriesTerminadas;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}
	
	public void anhadeFactura(Factura f) {
		facturas.add(f);
	}

	public Set<Capitulo> getCapitulosVistos() {
		return capitulosVistos;
	}
	
	public double verFactura(int anho, int mes) {
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
		double importe = factura.calculaImporteTotal();
		return importe;
	}
	
	
	public void anhadeSerie(Serie s) {
		if(!seriesPendientes.contains(s)) {
			seriesPendientes.add(s);
		}
	}
	public void comenzarSerie(Serie s) {
		if(seriesPendientes.contains(s)) {
			seriesPendientes.remove(s);
			seriesEmpezadas.add(s);
		}
	}
	
	public void verCapitulo(Capitulo c) {
		capitulosVistos.add(c);
		Serie serie = c.getTemporada().getSerie();
		comenzarSerie(serie);
				
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
			
			Cargo cargo = new Cargo(ahora, c);
			if(anhoFactura != anhoActual || mesFactura != mesActual) {
				ArrayList<Cargo> cargos = new ArrayList<Cargo>();
				cargos.add(cargo);
				Factura factura = new Factura(ahora, cargos, this);
				facturas.add(factura);
			}
			else {
				ultimaFactura.anhadeCargo(cargo);
			}
		}
		else {
			Cargo cargo = new Cargo(ahora, c);
			ArrayList<Cargo> cargos = new ArrayList<Cargo>();
			cargos.add(cargo);
			Factura factura = new Factura(ahora, cargos, this);
			facturas.add(factura);
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
