import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { ScheduleExamDialogService } from './schedule-exam-dialog.service';

@Component({
  selector: 'app-schedule-exam-dialog',
  templateUrl: './schedule-exam-dialog.component.html',
  styleUrls: ['./schedule-exam-dialog.component.scss']
})
export class ScheduleExamDialogComponent implements OnInit {
  form!: FormGroup;
  existentDate = null;

  constructor(
    private fb: FormBuilder,
    private readonly service: ScheduleExamDialogService,
    @Inject(MAT_DIALOG_DATA) private dialogData: { subjectExecutionId: string, examPeriodId: string },
    public readonly dialogRef: MatDialogRef<ScheduleExamDialogComponent>,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      date: [null, [Validators.required]]
    })
    this.service
      .fetchHeldExamIfExists(this.dialogData.examPeriodId, this.dialogData.subjectExecutionId)
      .pipe(
        first()
      )
      .subscribe(
        (res) => {
          this.existentDate = res?.body?.date;
        });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createHeldExam(this.dialogData.examPeriodId, this.dialogData.subjectExecutionId, form['date'] as Date).subscribe({
      next: (_) => {
        this.snack.open('Exam scheduled successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred scheduling exam');
      },
    });
  }
}
