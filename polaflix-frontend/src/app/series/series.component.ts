import { Component, OnInit } from '@angular/core';
import { Serie } from '../interfaces';
import { RouterModule } from '@angular/router';
import { SerieService } from '../serie.service';
import { UsuarioService } from '../usuario.service';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-series',
  standalone: true,
  imports: [RouterModule, NgFor, FormsModule, NgIf],
  templateUrl: './series.component.html',
  styleUrl: './series.component.css'
})

export class SeriesComponent implements OnInit {
  private nombreUsuario: string = '';
  private series: Serie[] = [];
  public seriesFiltradas: Serie[] = [];
  public busqueda: string = '';
  public diccionario: string[] = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ'.split('');
  public serieSeleccionada: Serie | undefined;
  public mensajeError: string = '';

  constructor(private serieService: SerieService, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.nombreUsuario = this.usuarioService.getNombreUsuario();
    this.getSeries();
  }

  getSeries(): void {
    this.serieService.getSeries().subscribe(series => {
      this.series = series;
      this.seriesFiltradas = series;
    });
  }

  getSeriesByInicial(inicial: string): void {
    this.seriesFiltradas = this.series.filter(serie => serie.titulo.startsWith(inicial));
    this.mensajeError = '';
    if (this.seriesFiltradas.length === 0) {
      this.mensajeError = `No hay ninguna serie que comience con la letra ${inicial}`;
    }
  }

  buscaSerie(): void {
    if (this.busqueda) {
      this.mensajeError = '';
      this.serieService.getSeries(this.busqueda).subscribe(series => {
        this.seriesFiltradas = series;
        if (this.seriesFiltradas.length === 0) {
          this.mensajeError = `No se encontraron series para la búsqueda "${this.busqueda}"`;
        }
      });
    } else {
      this.seriesFiltradas = this.series; 
    }
  }

  anhadeSerie(idSerie: number): void {
    this.usuarioService.anhadeSerie(this.nombreUsuario, idSerie).subscribe();
  }

  mostrarInfo(serie : Serie) : void {
    this.serieSeleccionada = serie;
  }

  ocultarInfo() : void {
    this.serieSeleccionada = undefined;
  }

}