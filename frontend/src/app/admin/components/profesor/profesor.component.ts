import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profesor',
  templateUrl: './profesor.component.html',
  styleUrls: ['./profesor.component.scss'],
})
export class ProfesorComponent implements OnInit {
  links = [
    ['list-profesors-tab', 'List profesors'],
    ['create-profesor-tab', 'Create profesor'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}
