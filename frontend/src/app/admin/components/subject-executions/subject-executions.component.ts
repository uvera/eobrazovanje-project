import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-subject-executions',
  templateUrl: './subject-executions.component.html',
  styleUrls: ['./subject-executions.component.scss'],
})
export class SubjectExecutionsComponent implements OnInit {
  links = [
    ['list-subject-executions-tab', 'List subject executions'],
    ['create-subject-executions-tab', 'Create subject execution'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
