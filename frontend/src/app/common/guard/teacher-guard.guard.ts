import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  CanLoad,
  Route,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree,
} from '@angular/router';
import { debounceTime, map, Observable } from 'rxjs';
import { CurrentUserService } from '../service/current-user.service';

@Injectable({
  providedIn: 'root',
})
export class TeacherGuard implements CanActivate, CanActivateChild, CanLoad {
  constructor(private service: CurrentUserService) {}

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
    return this.service.currentUser$.pipe(
      debounceTime(500),
      map((user) => (user ? user.role === 'STUDENT' : false))
    );
  }
}
