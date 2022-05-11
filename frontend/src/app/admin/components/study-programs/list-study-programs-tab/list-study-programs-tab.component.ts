import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { AreYouSureDialogComponent } from 'src/app/common/components/dialogs/are-you-sure-dialog/are-you-sure-dialog.component';
import { SubjectViewDTO } from '../../subjects/list-subjects-tab/list-subjects-tab.component';
import { EditStudyProgramDialogComponent } from '../edit-study-program-dialog/edit-study-program-dialog.component';
import { ListStudyProgramsTabService } from './list-study-programs-tab.service';


@Component({
  selector: 'app-list-study-programs-tab',
  templateUrl: './list-study-programs-tab.component.html',
  styleUrls: ['./list-study-programs-tab.component.scss']
})
export class ListStudyProgramsTabComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<StudyProgramsViewDTO[]>([]);

  constructor(
    private service: ListStudyProgramsTabService,
    private snack: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.pageNumberAndSizeCombined$.subscribe((value) => {
      this.fetchFromApi(value.pageIndex, value.pageSize);
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

  fetchFromApi(pageIndex: number, pageSize: number) {
    this.service
      .fetchPaged(pageIndex, pageSize)
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

  reloadFromApi() {
    this.fetchFromApi(this.pageIndex.value, this.pageSize.value);
  }

  queryParamsChange(event: NzTableQueryParams) {
    this.pageSize.next(event.pageSize);
    this.pageIndex.next(event.pageIndex);
  }

  editStudyProgram(id: string) {
    this.dialog
      .open(EditStudyProgramDialogComponent, {
        data: {
          id: id,
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

  deleteStudyProgram(id: string) {
    this.dialog
      .open(AreYouSureDialogComponent, {
        data: {
          dialogTitle: 'Are you sure you want to delete this study program?',
          yesButtonText: 'Delete',
        },
      })
      .afterClosed()
      .pipe(first())
      .subscribe({
        next: (value) => {
          if (value === 'yes') {
            this.service
              .deleteStudyProgramById(id)
              .pipe(first())
              .subscribe({
                next: (_) => {
                  this.snack.open('Successfully deleted study program');
                  this.reloadFromApi();
                },
                error: () => {
                  this.snack.open('Error while deleting study program');
                },
              });
          }
        },
      });
  }
  }

export interface StudyProgramsViewDTO {
  id: string;
  codeName: string;
  name: string;
  subjects: Array<{
    id: string;
    name: string;
    espb: number;
    year: number;
  }>;
}

@Pipe({name: 'subjectsNamePipe'})
export class SubjectsNamePipe implements PipeTransform {
  transform(value: SubjectViewDTO[]): any {
    return value.map(v => v.name).join(',')
  }
}
