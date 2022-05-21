import { Component, OnInit } from '@angular/core';
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
  readonly dataSet = new BehaviorSubject<SubjectViewDTO[]>([]);

  constructor(
    private readonly service: SubjectsComponentService
  ) { }

  ngOnInit(): void {
    this.fetchCurrentStudentSubjects()
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

  fetchCurrentStudentSubjects() {
    this.service.fetchCurrentUser()
    .pipe()
    .subscribe((res) => {
      const responseBody = res?.body;
      if (responseBody) {
        const { email }  = responseBody;
        this.service.
        fetchCurrentStudent(email)
          .subscribe((res) => {
            const responseBody = res?.body
            if (responseBody) {
              const { id }  = responseBody;
              this.service.fetchStudentSubjects(id)
              .subscribe((res) => {
                console.log(res?.body)
              })
            }
          })
        }
      })
    }

}
