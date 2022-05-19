import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  links = [
    ['list-student-payments-tab', 'List payments'],
    ['create-student-payment-tab', 'Create payment'],
  ];
  activeLink = this.links[0][0];

  constructor() { }

  ngOnInit(): void {
  }

}
