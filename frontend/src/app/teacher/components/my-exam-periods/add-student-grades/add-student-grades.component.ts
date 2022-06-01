import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';
import { HeldExamResultDTO, HeldExamViewDTO } from '../list-exam-periods/list-exam-periods.component';
import { ListExamPeriodsService } from '../list-exam-periods/list-exam-periods.service';
import { AddStudentGradesService } from './add-student-grades.service';

@Component({
  selector: 'app-add-student-grades',
  templateUrl: './add-student-grades.component.html',
  styleUrls: ['./add-student-grades.component.scss']
})
export class AddStudentGradesComponent implements OnInit {
  form!: FormGroup;
  opEnrolledStudents: EnrolledStudentDTO[] = [];
  opResults: HeldExamResultDTO[] = [];
  isCompleted: boolean = false;

  constructor(
    private fb: FormBuilder,
    private readonly service: AddStudentGradesService,
    private readonly mainService: ListExamPeriodsService,
    @Inject(MAT_DIALOG_DATA) private dialogData: { examPeriodId: string, subjectExecutionId: string, heldExam: HeldExamViewDTO },
    public readonly dialogRef: MatDialogRef<AddStudentGradesComponent>,
    private readonly snack: MatSnackBar,
  ) { }

  ngOnInit(): void {
    if (this.dialogData.heldExam.heldExamResults.length > 0) {
      this.isCompleted = true;
      this.opResults = this.dialogData.heldExam.heldExamResults
    }
    else {
      this.mainService.fetchEnrolledStudents(this.dialogData.examPeriodId, this.dialogData.subjectExecutionId)
        .pipe(first())
        .subscribe((res) => {
          if (Array.from(res.body).length == 0) {
            this.snack.open('No enrolled students.....');
            this.dialogRef.close('success');
          }
          else {
            this.opEnrolledStudents = res?.body as EnrolledStudentDTO[];
            let results = res?.body.reduce((acc: any, i: EnrolledStudentDTO) => {
              let key: string = `${i.studentId}`
              acc[key] = [null, [Validators.required]];
              return acc
            }, {})
            this.form = this.fb.group({
              ...results
            });
          }
        });
    }
  }

  submitForm(form: Record<string, unknown>) {
    const formValues = Object.entries(form).map((obj) => {
      return { studentId: obj[0], score: obj[1], heldExamId: this.dialogData.heldExam.id }
    })
    this.service.createResults(formValues).subscribe({
      next: (_) => {
        this.snack.open('Results added successfully.');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occured adding results');
      },
    });
  }
}

interface EnrolledStudentDTO {
  studentId: string;
  name: string;
  transcriptNumber: string;
  preExamActivitiesSum: number;
}
