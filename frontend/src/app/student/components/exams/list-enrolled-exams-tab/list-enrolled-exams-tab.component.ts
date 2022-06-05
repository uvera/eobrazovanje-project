import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { SubjectExecutionTableViewDTO } from 'src/app/admin/components/subject-executions/list-subject-executions-tab/list-subject-executions-tab.component';
import { HeldExamResultDTO, HeldExamViewDTO } from 'src/app/teacher/components/my-exam-periods/list-exam-periods/list-exam-periods.component';
import { ListExamResultsTabService } from '../list-exam-results-tab/list-exam-results-tab.service';
import { ListEnrolledExamsTabService } from './list-enrolled-exams-tab.service';

@Component({
  selector: 'app-list-enrolled-exams-tab',
  templateUrl: './list-enrolled-exams-tab.component.html',
  styleUrls: ['./list-enrolled-exams-tab.component.scss'],
})
export class ListEnrolledExamsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<ExamEnrollmentDTO[]>([]);
  form!: FormGroup;
  opExamPeriods: ExamPeriodsViewDTO[] = [];

  constructor(
    private service: ListEnrolledExamsTabService,
    private fb: FormBuilder,
    private resultsService: ListExamResultsTabService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.resultsService
      .getExamPeriods()
      .subscribe((res) => {
        this.opExamPeriods = res?.body ?? [];
      });
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(
        value.pageIndex,
        value.pageSize,
        this.form.get('id')?.value,
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

  fetchFromApi(pageIndex: number, pageSize: number, examPeriodId: string) {
    if (examPeriodId) {
      this.service
        .fetchPaged(pageIndex, pageSize, examPeriodId)
        .pipe(first())
        .subscribe((res) => {
          const responseBody = res?.body?.content;
          if (responseBody) {
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
    );
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }
}

interface ExamEnrollmentDTO {
  date: Date;
  subjectExecution: SubjectExecutionViewDTO;
}
