import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { Serie, Usuario } from '../interfaces';
import { Router, RouterModule} from '@angular/router';
import { NgFor, NgIf } from '@angular/common';

@Component({
  imports: [RouterModule, NgFor, NgIf],
  standalone: true,
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  usuario: Partial<Usuario> = {};
  nombreUsuario : string = "Paco";

  constructor(private usuarioService: UsuarioService, private router : Router) {}

  ngOnInit() {
    this.getUsuario(this.nombreUsuario);
  }

  getUsuario(nombre: string): void {
    this.usuarioService.getUsuario(nombre).subscribe(usuario => {
      this.usuario = usuario;
      console.log('Usuario:', this.usuario);
    });
  }

  verDetalles(serie: Serie): void {
    this.router.navigate(['/serie-details', serie.id]);
  } 
}  
