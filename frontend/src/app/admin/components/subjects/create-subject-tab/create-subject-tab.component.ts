import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateSubjectTabService } from './create-subject-tab.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

@Component({
  selector: 'app-create-subject-tab',
  templateUrl: './create-subject-tab.component.html',
  styleUrls: ['./create-subject-tab.component.scss'],
})
export class CreateSubjectTabComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateSubjectTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      espb: [null, [Validators.required, Validators.pattern('([0-9])*')]],
      year: [null, [Validators.required, Validators.pattern('([0-9])*')]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createSubject(form).subscribe({
      next: (_) => {
        this.snack.open('Subject created');
        of(
          this.router.navigate(['list-subjects-tab'], {
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
