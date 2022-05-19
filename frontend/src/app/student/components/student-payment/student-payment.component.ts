import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student-payment',
  templateUrl: './student-payment.component.html',
  styleUrls: ['./student-payment.component.scss']
})
export class StudentPaymentComponent implements OnInit {
  links = [
    ['list-student-payments-tab', 'List payments'],
    ['create-student-payment-tab', 'Create payment'],
  ];
  activeLink = this.links[0][0];

  constructor() { }

  ngOnInit(): void {
  }

}
