import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { SubjectExecutionViewDTO } from '../../subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { CreateExamPeriodTabService } from './create-exam-period-tab.service';

@Component({
  selector: 'app-create-exam-period-tab',
  templateUrl: './create-exam-period-tab.component.html',
  styleUrls: ['./create-exam-period-tab.component.scss'],
})
export class CreateExamPeriodTabComponent implements OnInit {
  form!: FormGroup;
  opSubjects: SubjectExecutionViewDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateExamPeriodTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.service
      .getSubjectExecutions()
      .pipe(first())
      .subscribe((res) => {
        this.opSubjects = res?.body!!;
      });
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      startDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      subjectExecutionIds: [null, Validators.required],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createExamPeriod(form).subscribe({
      next: (_) => {
        this.snack.open('Exam period created');
        of(
          this.router.navigate(['list-exam-periods-tab'], {
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
