import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Serie } from './interfaces';

@Injectable({
  providedIn: 'root'
})
export class SerieService {
  private url = 'http://localhost:8080/series';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  //GET series
  getSeries(titulo?: string, inicial?: string): Observable<Serie[]> {
    let url = this.url;
    if (titulo) {
      url += `?titulo=${titulo}`;
    } else if (inicial) {
      url += `?inicial=${inicial}`;
    }
    return this.http.get<Serie[]>(url)
      .pipe(
        tap(_ => this.log('fetched series')),
        catchError(this.handleError<Serie[]>('getSeries', []))
      );
  }

  //GET series/{id}
  getSerie(id: number): Observable<Serie> {
    const url = `${this.url}/${id}`;
    return this.http.get<Serie>(url).pipe(
      tap(_ => this.log(`fetched serie id=${id}`)),
      catchError(this.handleError<Serie>(`getSerie id=${id}`))
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
    console.log(`SerieService: ${message}`);
  }
}
