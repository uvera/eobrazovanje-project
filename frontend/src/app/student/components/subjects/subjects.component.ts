import { Component, OnInit } from '@angular/core';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { SubjectsComponentService, SubjectViewDTO } from './subjects.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss']
})
export class SubjectsComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<StudentViewSubjectsDTO[]>([]);

  constructor(
    private readonly service: SubjectsComponentService
  ) { }

  ngOnInit(): void {
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchCurrentStudentSubjects(value.pageIndex, value.pageSize);
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

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  fetchCurrentStudentSubjects(pageIndex: number, pageSize: number) {
    this.service.fetchCurrentUser()
      .pipe()
      .subscribe((res) => {
        const responseBody = res?.body;
        if (responseBody) {
          const { email } = responseBody;
          this.service.
            fetchCurrentStudent(email)
            .subscribe((res) => {
              const responseBody = res?.body
              if (responseBody) {
                const { id } = responseBody;
                this.service.fetchStudentSubjects(id, pageIndex, pageSize)
                  .subscribe((res) => {
                    const responseBody = res?.body;
                    if (responseBody) {
                      const { content, totalElements } = responseBody;
                      this.total.next(totalElements);
                      this.dataSet.next(content);
                    }
                  })
              }
            })
        }
      })
  }

}


interface StudentViewSubjectsDTO {
  id: string,
  year: number,
  subjectExecution: {
    place: string,
    time: Date,
    subject: {
      id: string,
      espb: number,
      name: string,
      year: number
    }
  }
}