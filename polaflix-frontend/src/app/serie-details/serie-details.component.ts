import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { SerieService } from '../serie.service';
import { UsuarioService } from '../usuario.service';
import { Serie, Temporada, Capitulo, Usuario, CapitulosVistos } from '../interfaces';
import { CommonModule, NgFor, NgIf } from '@angular/common';

@Component({
  standalone : true,
  imports : [RouterModule, NgFor, NgIf, CommonModule],
  selector: 'app-serie-details',
  templateUrl: './serie-details.component.html',
  styleUrls: ['./serie-details.component.css']
})

export class SerieDetailsComponent implements OnInit {
  serie: Serie | undefined;
  usuario: Usuario | undefined;
  capitulosVistos: Map<number, CapitulosVistos> = new Map();
  temporadaActual: Temporada | undefined;
  selectedCapitulo: Capitulo | undefined;

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private serieService: SerieService
  ) { }

  ngOnInit(): void {
    const idSerie = Number(this.route.snapshot.paramMap.get('id'));
    const nombreUsuario = 'Paco'; // Este valor debe ser dinámico según el contexto de tu aplicación
    this.getSerie(idSerie);
    this.getUsuario(nombreUsuario);
    this.getCapitulosVistos(nombreUsuario);
  }

  getSerie(serieId: number): void {
    this.serieService.getSerie(serieId).subscribe(serie => {
      this.serie = serie;
      this.temporadaActual = this.serie.temporadas[0];
    });
  }

  getUsuario(nombre: string): void {
    this.usuarioService.getUsuario(nombre).subscribe(usuario => {
      this.usuario = usuario;
    });
  }

  getCapitulosVistos(nombre: string): void {
    this.usuarioService.getCapitulosVistos(nombre).subscribe(capitulosVistos => {
      this.capitulosVistos = this.convertObjectToMap(capitulosVistos);
    });
  }

  private convertObjectToMap(obj: any): Map<number, CapitulosVistos> {
    const map = new Map<number, CapitulosVistos>();
    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        map.set(Number(key), obj[key]);
      }
    }
    return map;
  }

  cambiarTemporada(offset: number): void {
    if (this.serie && this.temporadaActual) {
      const newIndex = this.serie.temporadas.indexOf(this.temporadaActual) + offset;
      if (newIndex >= 0 && newIndex < this.serie.temporadas.length) {
        this.temporadaActual = this.serie.temporadas[newIndex];
      }
    }
  }

  esCapituloVisto(capitulo: Capitulo): boolean {
    if (this.serie && this.capitulosVistos) {
      const capitulosVistos = this.capitulosVistos.get(this.serie.id);
      const capitulos = capitulosVistos?.capitulos;
      if(capitulos) {
        for(const cap of capitulos) {
          if(cap.titulo == capitulo.titulo && capitulo.numero == cap.numero && this.temporadaActual?.numero == cap.numTemporada) {
            return true;
          }
        }
      }    
    }
    return false;
  }

  verCapitulo(capitulo: Capitulo): void {
    if (this.usuario && this.serie) {
      const nombreUsuario = this.usuario.nombre;
      const serieId = this.serie.id;
      const numCapitulo = capitulo.numero;

      if(this.temporadaActual) {
        this.usuarioService.verCapitulo(nombreUsuario, serieId, this.temporadaActual.numero , numCapitulo).subscribe(() => {
          this.getCapitulosVistos(nombreUsuario);
        });
      }
    }
  }

  mostrarDescripcion(capitulo: Capitulo): void {
    this.selectedCapitulo = capitulo;
  }

  cerrarDescripcion(): void {
    this.selectedCapitulo = undefined;
  }
}
