import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { SubjectExecutionTableViewDTO } from '../../subject-executions/list-subject-executions-tab/list-subject-executions-tab.component';
import { ListExamPeriodsTabService } from './list-exam-periods-tab.service';

@Component({
  selector: 'app-list-exam-periods-tab',
  templateUrl: './list-exam-periods-tab.component.html',
  styleUrls: ['./list-exam-periods-tab.component.scss']
})
export class ListExamPeriodsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<ExamPeriodsViewDTO[]>([]);

  constructor(
    private service: ListExamPeriodsTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(value.pageIndex, value.pageSize);
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

  fetchFromApi(pageIndex: number, pageSize: number) {
    this.service
      .fetchPaged(pageIndex, pageSize)
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

  reloadFromApi() {
    this.fetchFromApi(this.pageIndex.value, this.pageSize.value);
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }
}

interface ExamPeriodsViewDTO {
  id: string,
  name: string,
  startDate: Date,
  endDate: Date,
  subjectExecutions: SubjectExecutionTableViewDTO[]
}

@Pipe({name: 'subjectExecutionNamePipe'})
export class SubjectExecutionNamePipe implements PipeTransform {
  transform(value: SubjectExecutionTableViewDTO[]): any {
    return value.map(v => v.place).join(',')
  }
}
