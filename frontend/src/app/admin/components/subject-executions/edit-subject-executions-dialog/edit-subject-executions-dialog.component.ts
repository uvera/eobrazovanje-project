import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';
import { PreExamActivityViewDTO } from '../../pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { SubjectViewDTO } from '../../subjects/list-subjects-tab/list-subjects-tab.component';
import { TeacherViewDTO } from '../create-subject-executions-tab/create-subject-executions-tab.component';
import { CreateSubjectExecutionsTabService } from '../create-subject-executions-tab/create-subject-executions-tab.service';
import { EditSubjectExecutionsDialogService } from './edit-subject-executions-dialog.service';

@Component({
  selector: 'app-edit-subject-executions-dialog',
  templateUrl: './edit-subject-executions-dialog.component.html',
  styleUrls: ['./edit-subject-executions-dialog.component.scss'],
})
export class EditSubjectExecutionsDialogComponent implements OnInit {
  form!: FormGroup;
  opPreExamActivities: PreExamActivityViewDTO[] = [];
  opTeachers: TeacherViewDTO[] = [];
  existentPreExamActivities: string[] = [];
  existentTeachers: string[] = [];
  subjectName: string = '';
  opWeekDays = [
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY',
  ];

  constructor(
    private service: EditSubjectExecutionsDialogService,
    private mainService: CreateSubjectExecutionsTabService,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private dialogData: { id: string },
    public readonly dialogRef: MatDialogRef<EditSubjectExecutionsDialogComponent>,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.mainService
      .getPreExamActivities()
      .pipe(first())
      .subscribe((res) => {
        this.opPreExamActivities = res?.body as Array<PreExamActivityViewDTO>;
      });
    this.mainService
      .getProfessors()
      .pipe(first())
      .subscribe((res) => {
        this.opTeachers = res?.body!!;
      });
    this.form = this.fb.group({
      place: [null, [Validators.required]],
      time: [null, [Validators.required]],
      weekDay: [null, [Validators.required]],
      teacherIds: [null, [Validators.required]],
      preExamActivityIds: [null, [Validators.required]],
    });

    this.service.getSubjectById(this.dialogData.id).subscribe({
      next: (value) => {
        const body = value.body!;
        this.form.reset({
          place: body.place,
          time: body.time,
          weekday: body.weekDay,
          teacherIds: body.subjectProfessorEnrollments.map((a) => a.teacher),
          preExamActivityIds: body.preExamActivities,
        });
        this.subjectName = body.subject.name;
        this.opPreExamActivities = this.opPreExamActivities.concat(
          body.preExamActivities
        );
        //budz
        this.opTeachers = this.opTeachers.concat(
          body.subjectProfessorEnrollments.map((a) => a.teacher)
        );
        this.opTeachers = this.opTeachers.filter(
          (a, i) => this.opTeachers.findIndex((s) => a.id == s.id) === i
        );
        this.opPreExamActivities = this.opPreExamActivities.filter(
          (a, i) =>
            this.opPreExamActivities.findIndex((s) => a.id == s.id) === i
        );
        this.existentPreExamActivities = body.preExamActivities.map(
          (a) => a.id
        );
        this.existentTeachers = body.subjectProfessorEnrollments.map(
          (a) => a.teacher.id
        );
      },
      error: (_) => {
        this.snack.open(
          'Error occurred fetching subject execution for editing'
        );
        this.dialogRef.close('error');
      },
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.editSubjectById(this.dialogData.id, form).subscribe({
      next: (_) => {
        this.snack.open('Subject execution edited successfully');
        this.dialogRef.close('success');
      },
      error: (_) => {
        this.snack.open('Error occurred editing subject execution');
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

export interface StudentEnrollmentViewDTO {
  studentId: string;
  preExamActivitiesSum: number;
}
