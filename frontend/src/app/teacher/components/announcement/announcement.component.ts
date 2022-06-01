import { Component, OnInit } from '@angular/core';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { BehaviorSubject, combineLatest, first, map } from 'rxjs';
import { AnnouncementService } from './announcement.service';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.scss']
})
export class AnnouncementComponent implements OnInit {
  links = [
    ['list-announcement', 'List Announcement'],
    ['create-announcement', 'Create Announcement'],
  ];
  activeLink = this.links[0][0];

  constructor() {}

  ngOnInit(): void {}
}