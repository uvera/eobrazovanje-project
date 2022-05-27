import { HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

export type CommonParamType = {
  [param: string]: string | string[];
};

export type HttpParamsUnionCommon = CommonParamType | HttpParams;

export interface CommonApiService {
  get<T>(
    route: string,
    params?: HttpParamsUnionCommon
  ): Observable<HttpResponse<T>>;

  post<T>(route: string, body: any): Observable<HttpResponse<T>>;

  put<T>(route: string, body: any): Observable<HttpResponse<T>>;

  delete<T>(
    route: string,
    params?: HttpParamsUnionCommon
  ): Observable<HttpResponse<T>>;

  putForm<T>(route: string, body: FormData): Observable<HttpResponse<T>>;

  postForm<T>(route: string, body: FormData): Observable<HttpResponse<T>>;
}
