import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-exam-periods',
  templateUrl: './my-exam-periods.component.html',
  styleUrls: ['./my-exam-periods.component.scss']
})
export class MyExamPeriodsComponent implements OnInit {
  links = [
    ['list-exam-periods', 'List exam periods'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
