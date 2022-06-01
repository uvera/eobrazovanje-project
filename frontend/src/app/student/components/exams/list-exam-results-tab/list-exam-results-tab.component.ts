import { Component, OnInit } from '@angular/core';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HeldExamResultViewDTO, ListExamResultsTabService } from './list-exam-results-tab.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';

@Component({
  selector: 'app-list-exam-results-tab',
  templateUrl: './list-exam-results-tab.component.html',
  styleUrls: ['./list-exam-results-tab.component.scss']
})
export class ListExamResultsTabComponent implements OnInit {
  readonly math = Math;
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<any[]>([]);
  opExamPeriods: ExamPeriodsViewDTO[] = [];
  studentId: string = '';
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListExamResultsTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
    ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.service
    .fetchCurrentUser()
    .subscribe((res) => {
      const responseBody = res?.body;
      if (responseBody) {
        const { email } = responseBody;
        this.service.fetchCurrentStudent(email).subscribe((res) => {
          const responseBody = res?.body;
          if (responseBody) {
            const { id } = responseBody;
            this.studentId = id
        }})
      }
    });
    this.service
      .getExamPeriods()
      .subscribe((res) => {
        this.opExamPeriods = res?.body ?? [];
      });
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(
        value.pageIndex,
        value.pageSize,
        this.form.get('id')?.value,
        this.studentId
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

  fetchFromApi(pageIndex: number, pageSize: number, examPeriodId: string, studentId: string) {
    if (examPeriodId) {
      this.service
        .fetchPaged(pageIndex, pageSize, examPeriodId, studentId)
        .pipe(first())
        .subscribe((res) => {
          const responseBody = res?.body;
          if (responseBody) {
            console.log(responseBody)
            this.dataSet.next(responseBody);
          }
        });
    }
  }

  reloadFromApi() {
    this.fetchFromApi(
      this.pageIndex.value,
      this.pageSize.value,
      this.form.get('id')?.value,
      this.studentId
    );
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }
}
