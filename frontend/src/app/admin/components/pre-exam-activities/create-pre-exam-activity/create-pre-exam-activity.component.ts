import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { CreatePreExamActivityService } from './create-pre-exam-activity.service';

@Component({
  selector: 'app-create-pre-exam-activity',
  templateUrl: './create-pre-exam-activity.component.html',
  styleUrls: ['./create-pre-exam-activity.component.scss']
})
export class CreatePreExamActivityComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private readonly service: CreatePreExamActivityService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute,
  ) {}
  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      points: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createPreExamActivity(form).subscribe({
      next: (_) => {
        this.snack.open('Pre exam activity created');
        of(
          this.router.navigate(['list-pre-exam-activities'], {
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
