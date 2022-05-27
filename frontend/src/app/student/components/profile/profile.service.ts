import { Injectable } from '@angular/core';
import { ApiService } from '../../../common/service/api.service';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProfileComponentService {
  constructor(private readonly api: ApiService) {}

  fetchCurrentUser() {
    const params = new HttpParams();
    return this.api.get<CurrentTokenInfoDTO>('/api/auth/whoami', params);
  }

  fetchCurrentStudent(email: string) {
    const params = new HttpParams().set('email', email);
    return this.api.get<CurrentStudentDTO>('/api/student/whoami', params);
  }
}

export interface CurrentTokenInfoDTO {
  email: string;
  role: Number;
}

export interface CurrentStudentDTO {
  id: string;
  balance: number;
  transcriptNumber: string;
  identificationNumber: string;
  user: {
    firstName: string;
    email: string;
    lastName: string;
  };
}
