import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-exam-periods',
  templateUrl: './exam-periods.component.html',
  styleUrls: ['./exam-periods.component.scss'],
})
export class ExamPeriodsComponent implements OnInit {
  links = [
    ['list-exam-periods-tab', 'List exam periods'],
    ['create-exam-period-tab', 'Create exam period'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
