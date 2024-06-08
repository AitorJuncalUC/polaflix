export interface Usuario {
    nombre: string;
    contrasenha: string;
    IBAN: string;
    premium: boolean;
    seriesPendientes: Serie[];
    seriesEmpezadas: Serie[];
    seriesTerminadas: Serie[];
  }
  
  export interface Serie {
    id: number;
    titulo: string;
    sinopsis: string;
    categoria: string;
    temporadas: Temporada[];
    actores: string[];
    autores: string[];
  }
  
  
  export interface Temporada {
    numero: number;
    id: number;
    capitulos: Capitulo[];
  }
  
  export interface Capitulo {
    titulo: string;
    numero: number;
    numTemporada: number;
    tituloSerie: string;
    descripcion: string;
  }
  
  export interface CapitulosVistos {
    usuario: Usuario;
    capitulos: Capitulo[];
  }
  
  export interface Factura {
    fecha: Date;
    importeTotal: number;
    cargos: Cargo[];
  }
  
  export interface Cargo {
    fecha: Date;
    precio: number;
    nombreSerie: string;
    numTemporada: number;
    numCapitulo: number;
  }
  