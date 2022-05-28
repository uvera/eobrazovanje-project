import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-pre-exam-activities',
  templateUrl: './student-pre-exam-activities.component.html',
  styleUrls: ['./student-pre-exam-activities.component.scss']
})
export class StudentPreExamActivitiesComponent implements OnInit {
  links = [
    ['view-students', 'List students'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
