import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { PreExamActivityViewDTO } from '../../pre-exam-activities/edit-pre-exam-activity/edit-pre-exam-activity.component';
import { SubjectViewDTO } from '../../subjects/list-subjects-tab/list-subjects-tab.component';
import { CreateSubjectExecutionsTabService } from './create-subject-executions-tab.service';

@Component({
  selector: 'app-create-subject-executions-tab',
  templateUrl: './create-subject-executions-tab.component.html',
  styleUrls: ['./create-subject-executions-tab.component.scss']
})
export class CreateSubjectExecutionsTabComponent implements OnInit {
  form!: FormGroup;
  opSubjects: Array<SubjectViewDTO> = [];
  opPreExamActivities: Array<PreExamActivityViewDTO> = [];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateSubjectExecutionsTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute,
  ) {}
  ngOnInit(): void {
    this.service.getSubjects().pipe(first()).subscribe((res) => {
      this.opSubjects = res?.body as Array<SubjectViewDTO>
    })
    this.service.getPreExamActivities().pipe(first()).subscribe((res) => {
      this.opPreExamActivities = res?.body as Array<PreExamActivityViewDTO>
    })
    this.form = this.fb.group({
      place: [null, [Validators.required]],
      time: [null, [Validators.required]],
      subjectId: [null, [Validators.required]],
      preExamActivityIds: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createSubjectExecution(form).subscribe({
      next: (_) => {
        this.snack.open('Subject execution created created');
        of(
          this.router.navigate(['list-subject-executions-tab'], {
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
