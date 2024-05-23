export interface Usuario {
    nombre: string;
    contrasenha: string;
    IBAN: string;
    premium: boolean;
    seriesPendientes: Serie[];
    seriesEmpezadas: Serie[];
    seriesTerminadas: Serie[];
    facturas: Factura[];
    capitulosVistos: Map<number, CapitulosVistos>;
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
    serie: Serie;
    capitulos: Capitulo[];
  }
  
  export interface Capitulo {
    titulo: string;
    numero: number;
    numTemporada: number;
    tituloSerie: string;
    id: number;
    descripcion: string;
    temporada: Temporada;
  }
  
  export interface CapitulosVistos {
    id: number;
    usuario: Usuario;
    capitulos: Capitulo[];
  }
  
  export interface Factura {
    fecha: Date;
    importeTotal: number;
    id: number;
    cargos: Cargo[];
    usuario: Usuario;
  }
  
  export interface Cargo {
    id: number;
    fecha: Date;
    precio: number;
    nombreSerie: string;
    numTemporada: number;
    numCapitulo: number;
  }
  