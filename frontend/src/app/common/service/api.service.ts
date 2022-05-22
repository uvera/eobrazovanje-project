import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { SessionService } from './session.service';
import { CommonApiService, HttpParamsUnionCommon } from './api.service.i';

@Injectable({
  providedIn: 'root',
})
export class ApiService implements CommonApiService {
  private readonly API_URL = environment.apiUrl;
  private headers: HttpHeaders;

  private subscription: Subscription;

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {
    this.headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.subscription = sessionService.accessToken$.subscribe(
      (data) =>
        (this.headers = this.headers.set('Authorization', `Bearer ${data}`))
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  generateRouteUrl(route: string) {
    const generatedUrl = this.API_URL.endsWith('/')
      ? this.API_URL.substring(0, this.API_URL.length - 1)
      : this.API_URL;
    return `${generatedUrl}${route.startsWith('/') ? route : `/${route}`}`;
  }

  get<T>(
    route: string,
    params?: HttpParamsUnionCommon
  ): Observable<HttpResponse<T>> {
    return this.httpClient.get<T>(this.generateRouteUrl(route), {
      observe: 'response',
      params,
      headers: this.headers,
    });
  }

  post<T>(route: string, body?: any): Observable<HttpResponse<T>> {
    return this.httpClient.post<T>(this.generateRouteUrl(route), body, {
      observe: 'response',
      headers: this.headers,
    });
  }

  putForm<T>(route: string, body: FormData): Observable<HttpResponse<T>> {
    return this.httpClient.put<T>(this.generateRouteUrl(route), body, {
      observe: 'response',
      headers: this.headers, //.delete('Content-Type'),
    });
  }

  postForm<T>(route: string, body: FormData): Observable<HttpResponse<T>> {
    return this.httpClient.post<T>(this.generateRouteUrl(route), body, {
      observe: 'response',
      headers: this.headers, //.delete('Content-Type'),
    });
  }

  put<T>(route: string, body: any): Observable<HttpResponse<T>> {
    return this.httpClient.put<T>(this.generateRouteUrl(route), body, {
      observe: 'response',
      headers: this.headers,
    });
  }

  delete<T>(
    route: string,
    params?: HttpParamsUnionCommon
  ): Observable<HttpResponse<T>> {
    return this.httpClient.delete<T>(this.generateRouteUrl(route), {
      observe: 'response',
      params,
      headers: this.headers,
    });
  }
}
