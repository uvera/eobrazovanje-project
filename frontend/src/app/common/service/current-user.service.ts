import { Injectable, OnDestroy } from '@angular/core';
import { SessionService } from './session.service';
import { ApiService } from './api.service';
import {
  BehaviorSubject,
  debounceTime,
  first,
  Observable,
  Subscription,
} from 'rxjs';
import { CurrentUserDTO } from '../dto/current-user.dto';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CurrentUserService implements OnDestroy {
  private readonly _currentUser = new BehaviorSubject<CurrentUserDTO | null>(
    null
  );
  public readonly currentUser$: Observable<CurrentUserDTO | null>;
  public readonly subscription = new Subscription();

  constructor(
    private readonly session: SessionService,
    private readonly api: ApiService
  ) {
    this.currentUser$ = this._currentUser.asObservable();

    this.session.accessToken$.subscribe({
      next: (value) => {
        this.api
          .get<CurrentUserDTO>('/api/auth/whoami')
          .pipe(first())
          .subscribe({
            next: (user) => {
              this._currentUser.next(user.body);
            },
            
          });
      },
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
