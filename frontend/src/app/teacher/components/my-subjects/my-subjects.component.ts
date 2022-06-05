import { Component, OnInit } from '@angular/core';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { MySubjectsService, SubjectViewDTO } from './my-subjects.service';

@Component({
  selector: 'app-my-subjects',
  templateUrl: './my-subjects.component.html',
  styleUrls: ['./my-subjects.component.scss'],
})
export class MySubjectsComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<TeacherViewSubjectsDTO[]>([]);

  constructor(private readonly service: MySubjectsService) { }

  ngOnInit(): void {
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchCurrentTeacherSubjects(value.pageIndex, value.pageSize);
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

  fetchCurrentTeacherSubjects(pageIndex: number, pageSize: number) {
    this.service
      .fetchCurrentUser()
      .pipe()
      .subscribe((res) => {
        const responseBody = res?.body;
        if (responseBody) {
          const { email } = responseBody;
          this.service.fetchCurrentTeacher(email).subscribe((res) => {
            const responseBody = res?.body;
            if (responseBody) {
              const { id } = responseBody;
              this.service
                .fetchTeacherSubjects(id, pageIndex, pageSize)
                .subscribe((res) => {
                  const responseBody = res?.body;
                  if (responseBody) {
                    const { content, totalElements } = responseBody;
                    this.total.next(totalElements);
                    this.dataSet.next(
                      content[0]['subjectProfessorEnrollments']
                    );
                  }
                });
            }
          });
        }
      });
  }
}

export interface TeacherViewSubjectsDTO {
  subjectExecution: SubjectExecutionViewDTO;
  year: number;
}