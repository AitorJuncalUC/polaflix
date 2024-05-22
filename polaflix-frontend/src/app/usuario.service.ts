import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClientModule, HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Usuario, Factura, Capitulo, CapitulosVistos } from './interfaces';

@Injectable({
  providedIn: 'root'
})

export class UsuarioService {
  private usuariosUrl = 'http://localhost:8080/usuarios';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET usuarios from the server */
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.usuariosUrl)
      .pipe(
        tap(_ => this.log('fetched usuarios')),
        catchError(this.handleError<Usuario[]>('getUsuarios', []))
      );
  }

  /** GET usuario by nombre */
  getUsuario(nombre: string): Observable<Usuario> {
    const url = `${this.usuariosUrl}/${nombre}`;
    return this.http.get<Usuario>(url).pipe(
      tap(_ => this.log(`fetched usuario nombre=${nombre}`)),
      catchError(this.handleError<Usuario>(`getUsuario nombre=${nombre}`))
    );
  }

  /** GET facturas of a usuario by nombre */
  getFacturas(nombre: string, fecha?: string): Observable<Factura[]> {
    const url = fecha ? `${this.usuariosUrl}/${nombre}/facturas?fecha=${fecha}` : `${this.usuariosUrl}/${nombre}/facturas`;
    return this.http.get<Factura[]>(url).pipe(
      tap(_ => this.log(`fetched facturas for usuario nombre=${nombre}`)),
      catchError(this.handleError<Factura[]>(`getFacturas nombre=${nombre}`, []))
    );
  }


  /** GET capitulos vistos of a usuario by nombre */
  getCapitulosVistos(nombre: string): Observable<Map<number, CapitulosVistos>> {
    const url = `${this.usuariosUrl}/${nombre}/capitulosVistos`;
    return this.http.get<Map<number, CapitulosVistos>>(url).pipe(
      tap(_ => this.log(`fetched capitulos vistos for usuario nombre=${nombre}`)),
      catchError(this.handleError<Map<number, CapitulosVistos>>(`getCapitulosVistos nombre=${nombre}`))
    );
  }

  /** PUT: add a new capitulo visto */
  verCapitulo(nombre: string, idSerie: number, numTemporada: number, numCapitulo: number): Observable<Capitulo> {
    const url = `${this.usuariosUrl}/${nombre}/capitulosVistos?idSerie=${idSerie}&numTemporada=${numTemporada}&numCapitulo=${numCapitulo}`;
    return this.http.put<Capitulo>(url, this.httpOptions).pipe(
      tap(_ => this.log(`added capitulo visto for usuario nombre=${nombre}`)),
      catchError(this.handleError<Capitulo>('verCapitulo'))
    );
  }

  /** GET ultimo capitulo visto of a usuario by nombre and serie id */
  getUltimoCapitulo(nombre: string, idSerie: number): Observable<Capitulo> {
    const url = `${this.usuariosUrl}/${nombre}/capitulosVistos/ultimoVisto?idSerie=${idSerie}`;
    return this.http.get<Capitulo>(url).pipe(
      tap(_ => this.log(`fetched ultimo capitulo visto for usuario nombre=${nombre}, idSerie=${idSerie}`)),
      catchError(this.handleError<Capitulo>(`getUltimoCapitulo nombre=${nombre}, idSerie=${idSerie}`))
    );
  }

  /** PUT: add a new serie pendiente */
  anhadeSerie(nombre: string, idSerie: number): Observable<Usuario> {
    const url = `${this.usuariosUrl}/${nombre}/seriesPendientes?idSerie=${idSerie}`;
    return this.http.put<Usuario>(url, this.httpOptions).pipe(
      tap(_ => this.log(`added serie pendiente for usuario nombre=${nombre}`)),
      catchError(this.handleError<Usuario>('anhadeSerie'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(`UsuarioService: ${message}`);
  }
}
