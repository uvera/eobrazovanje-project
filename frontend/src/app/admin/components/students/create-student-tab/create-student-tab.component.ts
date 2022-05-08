import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateStudentTabService } from './create-student-tab.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-create-student-tab',
  templateUrl: './create-student-tab.component.html',
  styleUrls: ['./create-student-tab.component.scss'],
})
export class CreateStudentTabComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateStudentTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      identificationNumber: [null, [Validators.required]],
      transcriptNumber: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      currentYear: [
        null,
        [Validators.required, Validators.pattern('([0-9])*')],
      ],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createStudent(form).subscribe({
      next: (_) => {
        this.snack.open('Student created');
        of(
          this.router.navigate(['list-students-tab'], {
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
