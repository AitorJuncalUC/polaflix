import { CurrencyPipe, DatePipe, NgFor, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Cargo, Factura } from '../interfaces';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [RouterModule, NgFor, CurrencyPipe, DatePipe],
  templateUrl: './facturas.component.html',
  styleUrl: './facturas.component.css'
})

export class FacturasComponent implements OnInit {
  private facturas: Factura[] = [];
  public cargos: Cargo[] = [];
  public mesActual: string = '';
  private nombreUsuario: string = '';

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.mesActual = this.getMesActual();
    this.nombreUsuario = this.usuarioService.getNombreUsuario();
    this.getFacturas(this.nombreUsuario, this.mesActual);
  }

  getFacturas(nombre: string, fecha?: string): void {
    this.usuarioService.getFacturas(nombre, fecha).subscribe(facturas => {
      this.facturas = facturas;
      this.cargos = this.getCargos(facturas);
    });
  }

  getCargos(facturas: Factura[]): Cargo[] {
    return facturas.flatMap(factura => factura.cargos);
  }

  getImporteTotal(): number {
    if (this.facturas.length > 0) {
      return this.facturas[0].importeTotal;
    }
    return 0.0;
  }


  getMesActual(): string {
    const now = new Date();
    return formatDate(now, 'MM-yyyy', 'en-US');
  }

  mesAnterior(): void {
    const [mes, anho] = this.mesActual.split('-').map(Number);
    let mesAnterior = mes - 1;
    let anhoAnterior = anho;
  
    if (mesAnterior === 0) {
      mesAnterior = 12;
      anhoAnterior -= 1;
    }
  
    this.mesActual = `${mesAnterior.toString().padStart(2, '0')}-${anhoAnterior}`;
    const fechaAnterior = `${mesAnterior.toString().padStart(2, '0')}-${anhoAnterior}`;
    this.getFacturas(this.nombreUsuario, fechaAnterior);
  }
  
  mesSiguiente(): void {
    const [mes, anho] = this.mesActual.split('-').map(Number);
    let mesSiguiente = mes + 1;
    let anhoSiguiente = anho;
  
    if (mesSiguiente === 13) {
      mesSiguiente = 1;
      anhoSiguiente += 1;
    }
  
    this.mesActual = `${mesSiguiente.toString().padStart(2, '0')}-${anhoSiguiente}`;
    const fechaSiguiente = `${mesSiguiente.toString().padStart(2, '0')}-${anhoSiguiente}`;
    this.getFacturas(this.nombreUsuario, fechaSiguiente);
  }
  
}
