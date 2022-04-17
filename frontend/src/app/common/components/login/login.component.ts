import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SessionService } from '../../service/session.service';
import { ApiService } from '../../service/api.service';
import { TokenDTO } from '../../dto/token.dto';
import { first, of, tap } from 'rxjs';
import { CurrentUserDTO } from '../../dto/current-user.dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  doLoginForm!: FormGroup;
  readonly emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  readonly passwordFormControl = new FormControl('', [Validators.required]);

  constructor(
    private readonly fb: FormBuilder,
    private readonly snack: MatSnackBar,
    private readonly session: SessionService,
    private readonly api: ApiService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.doLoginForm = this.fb.group({
      email: this.emailFormControl,
      password: this.passwordFormControl,
    });
  }

  submitLogin() {
    const { email, password } = this.doLoginForm.value;
    const res = this.api
      .post<TokenDTO>('/api/auth/login', {
        email,
        password,
      })
      .pipe(first())
      .subscribe({
        next: (value) => {
          const body = value.body;
          if (!body) return;
          this.session.setAccessToken(body.accessToken);
          this.session.setRefreshToken(body.refreshToken);
          of(this.router.navigate(['dashboard']));
        },
        error: (_) => {
          this.snack.open('Bad login', undefined, { verticalPosition: 'top' });
        },
      });
  }
}
