import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.scss'],
})
export class ExamsComponent implements OnInit {
  links = [
    ['list-student-exam-enrollments-tab', 'List exam periods/Enroll'],
    ['list-student-all-exam-enrollments-tab', 'List scheduled exams'],
    ['list-student-all-exam-results-tab', 'List exam results'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
