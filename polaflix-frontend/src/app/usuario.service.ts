import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClientModule, HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Usuario, Factura, Capitulo, CapitulosVistos } from './interfaces';

@Injectable({
  providedIn: 'root'
})

export class UsuarioService {
  private url = 'http://localhost:8080/usuarios'; 
  private nombreUsuario: string = 'Paco';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getNombreUsuario(): string {
    return this.nombreUsuario;
  }

  //GET usuarios
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.url)
      .pipe(
        tap(_ => this.log('fetched usuarios')),
        catchError(this.handleError<Usuario[]>('getUsuarios', []))
      );
  }

  //GET usuarios/{nombre}
  getUsuario(nombre: string): Observable<Usuario> {
    const url = `${this.url}/${nombre}`;
    return this.http.get<Usuario>(url).pipe(
      tap(_ => this.log(`fetched usuario nombre=${nombre}`)),
      catchError(this.handleError<Usuario>(`getUsuario nombre=${nombre}`))
    );
  }

  //GET usuarios/{nombre}/facturas
  getFacturas(nombre: string, fecha?: string): Observable<Factura[]> {
    let url = `${this.url}/${nombre}/facturas`;
    if(fecha) {
      url = `${this.url}/${nombre}/facturas?fecha=${fecha}`;
    }
    return this.http.get<Factura[]>(url).pipe(
      tap(_ => this.log(`fetched facturas for usuario nombre=${nombre}`)),
      catchError(this.handleError<Factura[]>(`getFacturas nombre=${nombre}`, []))
    );
  }


  //GET usuarios/{nombre}/capitulosVistos
  getCapitulosVistos(nombre: string): Observable<Map<number, CapitulosVistos>> {
    const url = `${this.url}/${nombre}/capitulosVistos`;
    return this.http.get<Map<number, CapitulosVistos>>(url).pipe(
      tap(_ => this.log(`fetched capitulos vistos for usuario nombre=${nombre}`)),
      catchError(this.handleError<Map<number, CapitulosVistos>>(`getCapitulosVistos nombre=${nombre}`))
    );
  }

  //PUT usuarios/{nombre}/capitulosVistos
  verCapitulo(nombre: string, idSerie: number, numTemporada: number, numCapitulo: number): Observable<Capitulo> {
    const url = `${this.url}/${nombre}/capitulosVistos?idSerie=${idSerie}&numTemporada=${numTemporada}&numCapitulo=${numCapitulo}`;
    return this.http.put<Capitulo>(url, this.httpOptions).pipe(
      tap(_ => this.log(`added capitulo visto for usuario nombre=${nombre}`)),
      catchError(this.handleError<Capitulo>('verCapitulo'))
    );
  }

  //GET usuarios/{nombre}/capitulosVistos/ultimoVisto
  getUltimoCapitulo(nombre: string, idSerie: number): Observable<Capitulo> {
    const url = `${this.url}/${nombre}/capitulosVistos/ultimoVisto?idSerie=${idSerie}`;
    return this.http.get<Capitulo>(url).pipe(
      tap(_ => this.log(`fetched ultimo capitulo visto for usuario nombre=${nombre}, idSerie=${idSerie}`)),
      catchError(this.handleError<Capitulo>(`getUltimoCapitulo nombre=${nombre}, idSerie=${idSerie}`))
    );
  }

  //PUT usuarios/{nombre}/seriesPendientes
  anhadeSerie(nombre: string, idSerie: number): Observable<Usuario> {
    const url = `${this.url}/${nombre}/seriesPendientes?idSerie=${idSerie}`;
    return this.http.put<Usuario>(url, this.httpOptions).pipe(
      tap(_ => this.log(`added serie pendiente for usuario nombre=${nombre}`)),
      catchError(this.handleError<Usuario>('anhadeSerie'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(`UsuarioService: ${message}`);
  }
}
