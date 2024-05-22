import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { Usuario } from '../interfaces';
import { RouterModule} from '@angular/router';
import { NgFor, NgIf } from '@angular/common';

@Component({
  imports: [RouterModule, NgFor, NgIf],
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  usuario: Partial<Usuario> = { seriesPendientes: [], seriesEmpezadas: [], seriesTerminadas: [] };


  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.getUsuario("Paco");
  }

  getUsuario(nombre: string): void {
    this.usuarioService.getUsuario(nombre).subscribe(usuario => {
      this.usuario = usuario;
      console.log('Usuario:', this.usuario); // Intenta acceder a usuario aquí
    });
  }
}  
