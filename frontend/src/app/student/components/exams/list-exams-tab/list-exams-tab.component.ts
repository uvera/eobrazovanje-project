import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { AreYouSureDialogComponent } from 'src/app/common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { ListExamsTabService } from './list-exams-tab.service';

@Component({
  selector: 'app-list-exams-tab',
  templateUrl: './list-exams-tab.component.html',
  styleUrls: ['./list-exams-tab.component.scss']
})
export class ListExamsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<SubjectExecutionViewDTO[]>([]);
  opExamPeriods: ExamPeriodsViewDTO[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListExamsTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.service.getExamPeriods().pipe(first()).subscribe((res) => {
      this.opExamPeriods = res?.body ?? []
    })
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(value.pageIndex, value.pageSize, this.form.get('id')?.value);
    });
    this.form.valueChanges.subscribe(x => {
      this.reloadFromApi()
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
            this.total.next(totalElements);
            this.dataSet.next(content);
          }
        });
    }
  }

  reloadFromApi() {
    this.fetchFromApi(this.pageIndex.value, this.pageSize.value, this.form.get('id')?.value);
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  enrollInExam(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to enroll in this exam?',
          yesButtonText: 'Enroll',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .enrollInExam(this.form.get('id')?.value, id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully enrolled in exam');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while enrolling in exam');
                },
              });
          }
        },
      });
  }
}

