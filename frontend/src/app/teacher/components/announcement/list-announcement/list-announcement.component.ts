import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { ExamPeriodsViewDTO } from 'src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component';
import { StudentsViewDTO } from 'src/app/admin/components/students/list-students-tab/list-students-tab.component';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { AreYouSureDialogComponent } from 'src/app/common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { CurrentUserService } from 'src/app/common/service/current-user.service';
import { CreateAnnouncementService } from '../create-announcement/create-announcement.service';
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
  opSubjects: SubjectExecutionViewDTO[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListAnnouncementService,
    private snack: MatSnackBar,
    private dialog: MatDialog,
    private createService: CreateAnnouncementService,
    private currentUserService: CurrentUserService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.currentUserService
      .currentUser$
      .pipe()
      .subscribe((res) => {
        console.log(res)
        const id = res?.obj?.id
        this.createService
          .getSubjectExecutions(id)
          .pipe(first())
          .subscribe((res) => {
            if (res.body) {
              let values = res.body[0]
              this.opSubjects = values.subjectProfessorEnrollments.map((val) => {
                return val.subjectExecution
              })
            }
          });
      })
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
  id: String;
  postText: String;
  TeacherResponseDTO: {
    id: string;
    teacherType: string;
    user: {
      firstName: string;
      lastName: string;
    };
  }
}