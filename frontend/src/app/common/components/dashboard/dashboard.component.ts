import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';
import { debounceTime, of, Subscription } from 'rxjs';
import { CurrentUserService } from '../../service/current-user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, OnDestroy {
  private readonly subscription = new Subscription();

  constructor(
    private readonly router: Router,
    private readonly currentUserService: CurrentUserService
  ) {}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    console.log('dashboard component init');
    this.subscription.add(
      this.currentUserService.currentUser$.pipe(debounceTime(500)).subscribe({
        next: (user) => {
          console.log({ user });
          if (!user) return;
          const { role } = user;
          if (role === 'ADMIN') {
            console.log('Role ADMIN found!');
            of(this.router.navigate(['admin']));
            return;
          }
          if (role === 'STUDENT') {
            console.log('Role STUDENT found!');
            of(this.router.navigate(['student']));
            return;
          }
          if (role === 'TEACHER') {
            console.log('Role TEACHER found!');
            of(this.router.navigate(['teacher']));
            return;
          }
        },
      })
    );
  }
}
