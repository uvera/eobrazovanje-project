import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pre-exam-activities',
  templateUrl: './pre-exam-activities.component.html',
  styleUrls: ['./pre-exam-activities.component.scss']
})
export class PreExamActivitiesComponent implements OnInit {
  links = [
    ['list-pre-exam-activities', 'List pre exam activities'],
    ['create-pre-exam-activity', 'Create pre exam activity'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
