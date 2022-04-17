import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  Router,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree,
} from '@angular/router';
import { debounceTime, map, Observable, of, tap } from 'rxjs';
import { SessionService } from '../service/session.service';

@Injectable({
  providedIn: 'root',
})
export class PublicGuard implements CanActivate, CanActivateChild, CanLoad {
  constructor(private session: SessionService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.handle();
  }
  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.handle();
  }
  canLoad(
    route: Route,
    segments: UrlSegment[]
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.handle();
  }

  handle(): Observable<boolean> {
    return this.session.accessToken$.pipe(
      debounceTime(500),
      map((x) => x === null),
      tap((x) => {
        if (!x) {
          of(this.router.navigate(['dashboard']));
        }
      })
    );
  }
}
