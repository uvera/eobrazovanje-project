import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { StudentsViewDTO } from 'src/app/admin/components/students/list-students-tab/list-students-tab.component';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { AreYouSureDialogComponent } from 'src/app/common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { AddStudentGradesComponent } from '../add-student-grades/add-student-grades.component';
import { ScheduleExamDialogComponent } from '../schedule-exam-dialog/schedule-exam-dialog.component';
import { ListExamPeriodsService } from './list-exam-periods.service';

@Component({
  selector: 'app-list-exam-periods',
  templateUrl: './list-exam-periods.component.html',
  styleUrls: ['./list-exam-periods.component.scss']
})
export class ListExamPeriodsComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<SubjectExecutionViewDTO[]>([]);
  opExamPeriods: ExamPeriodsViewDTO[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListExamPeriodsService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.service
      .getExamPeriods()
      .pipe(first())
      .subscribe((res) => {
        this.opExamPeriods = res?.body ?? [];
      });
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(
        value.pageIndex,
        value.pageSize,
        this.form.get('id')?.value
      );
    });
    this.form.valueChanges.subscribe((x) => {
      this.reloadFromApi();
    });
  }

  pageIndex$ = this.pageIndex.asObservable();
  pageSize$ = this.pageSize.asObservable();
  pageNumberAndSizeCombined$ = combineLatest([
    this.pageIndex$,
    this.pageSize$,
  ]).pipe(
    map((x) => ({
      pageIndex: x[0],
      pageSize: x[1],
    }))
  );

  fetchFromApi(pageIndex: number, pageSize: number, id: string) {
    if (id) {
      this.service
        .fetchPaged(pageIndex, pageSize, id)
        .pipe(first())
        .subscribe((res) => {
          const responseBody = res?.body;
          if (responseBody) {
            const { content, totalElements } = responseBody;
            console.log(content)
            this.total.next(totalElements);
            this.dataSet.next(content);
          }
        });
    }
  }

  reloadFromApi() {
    this.fetchFromApi(
      this.pageIndex.value,
      this.pageSize.value,
      this.form.get('id')?.value
    );
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  verifyExam(id: string) {
    this.dialog
      .open(ScheduleExamDialogComponent, {
        data: {
          subjectExecutionId: id,
          examPeriodId: this.form.get('id')?.value
        },
      })
      .afterClosed()
      .subscribe({
        next: (value) => {
          if (value === 'success') {
            this.reloadFromApi();
          }
        },
      });
  };

  completeExam(subjectExecutionID: string) {
    this.service.fetchHeldExamIfExists(this.form.get('id')?.value, subjectExecutionID)
      .pipe(first())
      .subscribe((res) => {
        this.dialog
          .open(AddStudentGradesComponent, {
            data: {
              examPeriodId: this.form.get('id')?.value,
              subjectExecutionId: subjectExecutionID,
              heldExam: res?.body
            },
          })
          .afterClosed()
          .pipe(first())
          .subscribe({
            next: (value) => {
              if (value === 'success') {
                this.reloadFromApi()
              }
            },
          });

      }, (err) => {
        this.snack.open('You first need to verify this exam');
      });


  }
}

export interface HeldExamViewDTO {
  id: string;
  date: Date;
  heldExamResults: HeldExamResultDTO[] 
}

export interface HeldExamResultDTO {
  student: StudentsViewDTO;
  score: string;
}
