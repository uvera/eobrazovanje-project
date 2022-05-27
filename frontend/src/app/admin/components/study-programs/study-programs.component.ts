import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-study-programs',
  templateUrl: './study-programs.component.html',
  styleUrls: ['./study-programs.component.scss'],
})
export class StudyProgramsComponent implements OnInit {
  links = [
    ['list-study-programs-tab', 'List study programs'],
    ['create-study-program-tab', 'Create study program'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
