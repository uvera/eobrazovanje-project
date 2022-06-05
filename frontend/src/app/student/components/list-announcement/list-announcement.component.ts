import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { TeacherResponseDTO } from 'src/app/admin/components/profesor/list-profesor-tab/list-profesor-tab.component';
import { StudentViewSubjectsDTO } from '../subjects/subjects.component';
import { ListAnnouncementService } from './list-announcement.service';

@Component({
  selector: 'app-list-announcement',
  templateUrl: './list-announcement.component.html',
  styleUrls: ['./list-announcement.component.scss']
})
export class ListAnnouncementComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<AnnouncementViewDTO[]>([]);
  opSubjects: StudentViewSubjectsDTO[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListAnnouncementService,
    private listService: ListAnnouncementService,
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.listService
      .fetchStudentSubjects()
      .pipe(first())
      .subscribe((res) => {
        if (res.body) {
          this.opSubjects = res.body
        }
      });
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      let val = this.form.get('id')?.value
      if (val) {
        this.fetchFromApi(
          this.form.get('id')?.value,
          value.pageIndex,
          value.pageSize,
        );
      }
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

  fetchFromApi(id: string, pageIndex: number, pageSize: number) {
    if (id) {
      this.service
        .fetchPaged(id, pageIndex, pageSize)
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

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  reloadFromApi() {
    this.fetchFromApi(
      this.form.get('id')?.value,
      this.pageIndex.value,
      this.pageSize.value,
    );
  }
}

export interface AnnouncementViewDTO {
  id: string;
  postText: string;
  teacher: TeacherResponseDTO;
}