import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateProfesorTabService } from './create-profesor-tab.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-create-profesor-tab',
  templateUrl: './create-profesor-tab.component.html',
  styleUrls: ['./create-profesor-tab.component.scss'],
})
export class CreateProfesorTabComponent implements OnInit {
  form!: FormGroup;
  teacherTypes = ['PROFESSOR', 'ASSISTANT'];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateProfesorTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      teacherType: [null, [Validators.required]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createProfesor(form).subscribe({
      next: (_) => {
        this.snack.open('Profesor created');
        of(
          this.router.navigate(['list-profesors-tab'], {
            relativeTo: this.ar.parent,
          })
        );
      },
      error: (_) => {
        this.snack.open('Error occurred');
      },
    });
  }
}

enum TeacherType {
  PROFESSOR,
  ASSISTANT,
}

interface TeacherTypeDTO {}
