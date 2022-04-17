import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, first, Observable, of, Subscription } from 'rxjs';
import { CurrentUserDTO } from '../dto/current-user.dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
  useFactory: () =>
    new SessionService(
      localStorage.getItem('access-token'),
      localStorage.getItem('refresh-token')
    ),
})
export class SessionService implements OnDestroy {
  private readonly API_URL = environment.apiUrl;

  private _accessToken: BehaviorSubject<string | null>;
  private _refreshToken: BehaviorSubject<string | null>;
  private subscription: Subscription = new Subscription();
  public readonly accessToken$: Observable<string | null>;
  public readonly refreshToken$: Observable<string | null>;

  constructor(accessToken: string | null, refreshToken: string | null) {
    this._accessToken = new BehaviorSubject<string | null>(null);
    this.accessToken$ = this._accessToken.asObservable();
    this._accessToken.next(accessToken ?? null);
    this.subscription.add(
      this.accessToken$.subscribe((value) => {
        if (value) {
          localStorage.setItem('access-token', value);
        } else {
          localStorage.removeItem('access-token');
        }
      })
    );

    this._refreshToken = new BehaviorSubject<string | null>(null);
    this.refreshToken$ = this._refreshToken.asObservable();
    this._refreshToken.next(refreshToken ?? null);
    this.subscription.add(
      this.refreshToken$.subscribe((value) => {
        if (value) localStorage.setItem('refresh-token', value);
        else localStorage.removeItem('refresh-token');
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public setAccessToken(token: string | null) {
    this._accessToken.next(token);
  }

  public setRefreshToken(token: string | null) {
    this._refreshToken.next(token);
  }

  public getRefreshToken(): string | null {
    return this._refreshToken.getValue();
  }

  public clearAccessToken() {
    this.setAccessToken(null);
  }

  public clearRefreshToken() {
    this.setRefreshToken(null);
  }

  private generateRouteUrl(route: string) {
    const generatedUrl = this.API_URL.endsWith('/')
      ? this.API_URL.substring(0, this.API_URL.length - 1)
      : this.API_URL;
    return `${generatedUrl}${route.startsWith('/') ? route : `/${route}`}`;
  }
}
