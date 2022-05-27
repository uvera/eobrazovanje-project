import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';

@Component({
  selector: 'app-student-dashboard',
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.scss'],
})
export class StudentDashboardComponent implements OnInit {
  readonly pageIndex = new BehaviorSubject<number>(1);
  readonly total = new BehaviorSubject<number>(1);
  readonly pageSize = new BehaviorSubject<number>(10);
  readonly dataSet = new BehaviorSubject<SubjectViewDTO[]>([]);

  constructor() {}

  ngOnInit(): void {}
}

export interface SubjectViewDTO {
  id: string;
  espb: number;
  name: string;
  year: number;
}
