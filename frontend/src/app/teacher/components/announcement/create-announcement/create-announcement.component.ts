import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { CreateAnnouncementService } from './create-announcement.service';

@Component({
  selector: 'app-create-announcement',
  templateUrl: './create-announcement.component.html',
  styleUrls: ['./create-announcement.component.scss']
})
export class CreateAnnouncementComponent implements OnInit {
  form!: FormGroup;
  opSubjects: SubjectExecutionViewDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateAnnouncementService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.fetchTeacher()
    this.form = this.fb.group({
      postText: [null, [Validators.required]],
      subjectExecutionId: [null, [Validators.required]],
    });
  }

  fetchTeacher() {
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
            }
          });
        }
      });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createAnnouncement(form).subscribe({
      next: (_) => {
        this.snack.open('Announcement created');
        of(
          this.router.navigate(['list-announcement'], {
            relativeTo: this.ar.parent,
          })
        );
      },
      error: (_) => {
        this.snack.open('Error occurred');
      },
    });
  }
}

export interface SubjectExecutionViewDTO {
  id: string;
  place: string;
  time: string;
  weekDay: string;
  preExamActivities: PreExamActivityViewDTO[];
  subject: SubjectViewDTO;
  subjectProfessorEnrollments: {
    teacher: TeacherViewDTO;
  }[];
}

export interface PreExamActivityViewDTO {
  id: string;
  subjectProfessorEnrollments: {
    year: number;
    subjectExecution: SubjectExecutionViewDTO
  }[];
}

export interface TeacherEnrollmentViewDTO {
  id: string;
  subjectProfessorEnrollments: {
    year: number;
    subjectExecution: SubjectExecutionViewDTO
  }[];
}

export interface SubjectViewDTO {
  id: string;
  espb: number;
  name: string;
  year: number;
}

export interface TeacherViewDTO {
  id: string;
  teacherType: string;
  user: {
    firstName: string;
    lastName: string;
    email: string;
  };
}