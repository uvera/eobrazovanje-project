import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../service/session.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent implements OnInit {
  constructor(
    private readonly router: Router,
    private readonly session: SessionService
  ) {}

  ngOnInit(): void {
    this.session.clearRefreshToken();
    this.session.clearAccessToken();
    of(this.router.navigate(['login']));
  }
}
