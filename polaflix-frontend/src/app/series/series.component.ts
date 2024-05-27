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
  nombreUsuario: string = 'Paco';
  series: Serie[] = [];
  seriesFiltradas: Serie[] = [];
  busqueda: string = '';
  diccionario: string[] = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ'.split('');
  serieSeleccionada: Serie | null = null;

  constructor(private serieService: SerieService, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
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
  }

  buscaSeries(): void {
    if (this.busqueda) {
      this.serieService.getSeries(this.busqueda).subscribe(series => {
        this.seriesFiltradas = series;
      });
    } else {
      this.seriesFiltradas = this.series;
    }
  }

  anhadeSerie(idSerie: number): void {
    this.usuarioService.anhadeSerie(this.nombreUsuario, idSerie).subscribe();
  }

  muestraInfo(serie: Serie): void {
    this.serieSeleccionada = serie;
  }

  ocultaInfo(): void {
    this.serieSeleccionada = null;
  }

  gestionInfo(serie: Serie): void {
    if (this.serieSeleccionada && this.serieSeleccionada.id === serie.id) {
      this.ocultaInfo();
    } else {
      this.serieSeleccionada = serie;
    }
  }
}