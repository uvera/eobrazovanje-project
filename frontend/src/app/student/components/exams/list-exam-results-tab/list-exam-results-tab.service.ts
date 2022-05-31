import { HttpParams } from '@angular/common/http';
import { ApiService } from "src/app/common/service/api.service";
import { Injectable } from '@angular/core';
import { ExamPeriodsViewDTO } from "src/app/admin/components/exam-periods/list-exam-periods-tab/list-exam-periods-tab.component";
import { PageEntity } from "src/app/common/http/page-entity";

@Injectable({
    providedIn: 'root',
  })
export class ListExamResultsTabService {
    constructor(private readonly api: ApiService) {}
    
    fetchPaged(pageIndex: number, pageSize: number, examPeriodId: string, studentId: string) {
        const params = new HttpParams()
        .set('page', pageIndex)
        .set('records', pageSize)
        .set('examPeriodID', examPeriodId)
        .set('studentId', studentId);
      return this.api.get<HeldExamResultViewDTO[]>(
        `/api/held-exam/results`,
        params
      );
    }

    getExamPeriods() {
        return this.api.get<ExamPeriodsViewDTO[]>('/api/admin/exam-period/all');
    }
    
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
  transcriptNumber: string;
  identificationNumber: string;
  user: {
    firstName: string;
    email: string;
    lastName: string;
  };
}

export interface HeldExamResultViewDTO {
    subject: string;
    date: string;
    score: number
}