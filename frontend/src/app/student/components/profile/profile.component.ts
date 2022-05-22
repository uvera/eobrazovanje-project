import { Component, OnInit } from '@angular/core';
import { CurrentStudentDTO, ProfileComponentService } from './profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  student: CurrentStudentDTO = {
    id: '',
    transcriptNumber: '',
    identificationNumber: '',
    user: {
      firstName: '',
      email: '',
      lastName: ''
    }
  }

  constructor(
    private readonly service: ProfileComponentService
  ) { }

  ngOnInit(): void {
    this.fetchCurrentStudentInfo()
  }

  fetchCurrentStudentInfo() {
    this.service.fetchCurrentUser()
    .pipe()
    .subscribe((res) => {
      const responseBody = res?.body;
      if (responseBody) {
        const { email }  = responseBody;
        this.service.
        fetchCurrentStudent(email)
          .subscribe((res) => {
            const responseBody = res?.body
            if (responseBody)
              this.student = responseBody
            console.log(this.student)
          })
        }
      })
    }
}