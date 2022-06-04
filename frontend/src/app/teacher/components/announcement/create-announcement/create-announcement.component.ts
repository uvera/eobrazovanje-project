import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { SubjectExecutionViewDTO } from 'src/app/admin/components/subject-executions/edit-subject-executions-dialog/edit-subject-executions-dialog.component';
import { CurrentUserService } from 'src/app/common/service/current-user.service';
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
    private readonly ar: ActivatedRoute,
    private readonly currentUserService: CurrentUserService
  ) { }

  ngOnInit(): void {
    this.fetchTeacher()
    this.form = this.fb.group({
      postText: [null, [Validators.required]],
      subjectExecutionId: [null, [Validators.required]],
    });
  }

  fetchTeacher() {
    this.currentUserService
      .currentUser$
      .pipe()
      .subscribe((res) => {
        console.log(res)
        const id = res?.obj?.id
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
      })
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



export interface TeacherEnrollmentViewDTO {
  id: string;
  subjectProfessorEnrollments: {
    year: number;
    subjectExecution: SubjectExecutionViewDTO
  }[];
}