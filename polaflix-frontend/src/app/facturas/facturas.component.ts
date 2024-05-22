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
  facturas: Factura[] = [];
  cargos: Cargo[] = [];
  mesActual: string = '';

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.mesActual = this.getMesActual();
    this.getFacturas('Paco', this.mesActual);
  }

  getFacturas(nombre: string, fecha?: string): void {
    this.usuarioService.getFacturas(nombre, fecha).subscribe(facturas => {
      this.facturas = facturas;
      this.cargos = this.extractCargos(facturas);
      console.log('Facturas:', this.facturas);
    });
  }

  extractCargos(facturas: Factura[]): Cargo[] {
    return facturas.flatMap(factura => factura.cargos);
  }

  getTotal(): number {
    return this.cargos.reduce((total, cargo) => total + cargo.precio, 0);
  }


  getMesActual(): string {
    const now = new Date();
    return formatDate(now, 'MM-yyyy', 'en-US'); // Ajuste de formato
  }

  previousMonth(): void {
    const [currentMonth, currentYear] = this.mesActual.split('-').map(Number);
    let previousMonth = currentMonth - 1;
    let previousYear = currentYear;
  
    if (previousMonth === 0) {
      previousMonth = 12;
      previousYear -= 1;
    }
  
    this.mesActual = `${previousMonth.toString().padStart(2, '0')}-${previousYear}`;
    const fechaAnterior = `${previousMonth.toString().padStart(2, '0')}-${previousYear}`;
    this.getFacturas('Paco', fechaAnterior);
  }
  
  nextMonth(): void {
    const [currentMonth, currentYear] = this.mesActual.split('-').map(Number);
    let nextMonth = currentMonth + 1;
    let nextYear = currentYear;
  
    if (nextMonth === 13) {
      nextMonth = 1;
      nextYear += 1;
    }
  
    this.mesActual = `${nextMonth.toString().padStart(2, '0')}-${nextYear}`;
    const fechaSiguiente = `${nextMonth.toString().padStart(2, '0')}-${nextYear}`;
    this.getFacturas('Paco', fechaSiguiente);
  }
  
}
