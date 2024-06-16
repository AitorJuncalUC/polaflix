import { Component, OnInit } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  public title = 'Polaflix';
  public nombreUsuario: string | undefined;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.nombreUsuario = this.usuarioService.getNombreUsuario().toUpperCase();
  }
}
