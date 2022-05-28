import { Component, OnInit } from '@angular/core';
import { CreateDocumentTabService } from './create-document-tab.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create-document-tab',
  templateUrl: './create-document-tab.component.html',
  styleUrls: ['./create-document-tab.component.scss'],
})
export class CreateDocumentTabComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private readonly service: CreateDocumentTabService,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null, [Validators.required]],
      files: [null, [Validators.required]],
    });
  }

  onFileSelected(event: any): void {
    this.form.patchValue({ files: event?.target?.files ?? null });
  }

  submitForm(form: Record<string, unknown>) {
    this.service
      .upload(form['name'] as string, form['files'] as FileList)
      .subscribe({
        next: (_) => {
          this.snack.open('Upload successful');
        },
        error: (_) => {
          this.snack.open('Upload failed');
        },
      });
  }
}
