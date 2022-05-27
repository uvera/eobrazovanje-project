import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { first, of } from 'rxjs';
import { SubjectViewDTO } from '../../subjects/list-subjects-tab/list-subjects-tab.component';
import { CreateStudyProgramTabService } from './create-study-program-tab.service';

@Component({
  selector: 'app-create-study-program-tab',
  templateUrl: './create-study-program-tab.component.html',
  styleUrls: ['./create-study-program-tab.component.scss'],
})
export class CreateStudyProgramTabComponent implements OnInit {
  form!: FormGroup;
  opSubjects: Array<SubjectViewDTO> = [];

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateStudyProgramTabService,
    private readonly snack: MatSnackBar,
    private readonly router: Router,
    private readonly ar: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.service
      .getAvailableSubjects()
      .pipe(first())
      .subscribe((res) => {
        this.opSubjects = res?.body as Array<SubjectViewDTO>;
      });
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      codeName: [null, [Validators.required]],
      subjects: [null, [Validators.required]],
    });
  }

  submitForm(form: Record<string, unknown>) {
    this.service.createStudyProgram(form).subscribe({
      next: (_) => {
        this.snack.open('Study program created');
        of(
          this.router.navigate(['list-study-programs-tab'], {
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
