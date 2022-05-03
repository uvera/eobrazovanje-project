import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit {
  links = [
    ['list-subjects-tab', 'List subjects'],
    ['create-subject-tab', 'Create subject'],
  ];
  activeLink = this.links[0][0];
  constructor() {}

  ngOnInit(): void {}
}
