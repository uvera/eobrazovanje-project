import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { MySubjectsComponent } from '../../my-subjects/my-subjects.component';
import { MySubjectsService } from '../../my-subjects/my-subjects.service';
import { AddPreExamActivityResultDialogComponent } from '../add-pre-exam-activity-result-dialog/add-pre-exam-activity-result-dialog.component';
import { ListStudentsService } from './list-students.service';

@Component({
  selector: 'app-list-students',
  templateUrl: './list-students.component.html',
  styleUrls: ['./list-students.component.scss']
})
export class ListStudentsComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<TeacherTableViewStudentsDTO[]>([]);
  opSubjects: any[] = [];
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: ListStudentsService,
    private snack: MatSnackBar,
    private mainService: MySubjectsService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
    });
    this.fetchCurrentTeacherSubjects()
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


  fetchCurrentTeacherSubjects() {
    this.mainService
      .fetchCurrentUser()
      .pipe()
      .subscribe((res) => {
        const responseBody = res?.body;
        if (responseBody) {
          const { email } = responseBody;
          this.mainService.fetchCurrentTeacher(email).subscribe((res) => {
            const responseBody = res?.body;
            if (responseBody) {
              const { id } = responseBody;
              this.service
                .fetchTeacherSubjects(id)
                .subscribe((res) => {
                  const responseBody = res?.body;
                  if (responseBody) {
                    this.opSubjects = responseBody[0].subjectProfessorEnrollments.map((a) => {
                      let obj = { id: a.subjectExecution.id, name: a.subjectExecution.subject.name }
                      return obj
                    })
                    // this.opSubjects = responseBody[0]['subjectProfessorEnrollments'].map();
                  }
                });
            }
          });
        }
      });
  }
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

  editPreExamActivities(studentId: string) {
    this.dialog
      .open(AddPreExamActivityResultDialogComponent, {
        data: {
          studentId: studentId,
          subjExId: this.form.get('id')?.value
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
  }
}

export interface TeacherViewSubjectsDTO {
  subjectExecution: SubjectExecutionViewDTO;
  year: number;
}

export interface TeacherViewStudents {
  subjectProfessorEnrollments: TeacherViewSubjectsDTO[]
}

interface TeacherTableViewStudentsDTO {
  student: {
    id: string;
    transcriptNumber: string;
    user: {
      email: string;
      firstName: string;
      lastName: string;
    }
  }
}