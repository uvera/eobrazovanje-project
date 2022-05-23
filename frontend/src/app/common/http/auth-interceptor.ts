import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {catchError, Observable, of, switchMap, tap, throwError} from "rxjs";
import {SessionService} from "../service/session.service";
import {ApiService} from "../service/api.service";
import {TokenDTO} from "../dto/token.dto";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private sessionService: SessionService, private api: ApiService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // catch the error, make specific functions for catching specific errors and you can chain through them with more catch operators
    return next.handle(req).pipe(catchError(error => {
      if (error.status == 401 || error.status == 403) {
        return this.reAuth().pipe(
          catchError(() => {
            this.sessionService.clearAccessToken();
            this.sessionService.clearRefreshToken();
            return of(this.router.navigateByUrl("/login"));
          }),
          switchMap(() => next.handle(req)),
        );
      }
      return throwError(error);
    })); //here use an arrow function, otherwise you may get "Cannot read property 'navigate' of undefined" on angular 4.4.2/net core 2/webpack 2.70
  }

  private reAuth(): Observable<HttpResponse<TokenDTO>> {
    return this.api.post<TokenDTO>('/api/auth/refresh', {token: this.sessionService.getRefreshToken()})
      .pipe(tap(x => {
        this.sessionService.setAccessToken(x.body?.accessToken ?? null)
        this.sessionService.setRefreshToken(x.body?.refreshToken ?? null)
      }))
  }
}
