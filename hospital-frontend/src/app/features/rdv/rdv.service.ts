import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RdvResponse {
  id: number;
  doctorId: number;
  patientId: number;
  date: string;   // backend sends date as string
  time: string;   // time as string
  status: string;
}

export interface RdvRequest {
  doctorId: number;
  patientId: number;
  date: string;
  time: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class RdvService {

  private apiUrl = 'http://localhost:8084/api/appointments';

  constructor(private http: HttpClient) {}

  getAll(): Observable<RdvResponse[]> {
    return this.http.get<RdvResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<RdvResponse> {
    return this.http.get<RdvResponse>(`${this.apiUrl}/${id}`);
  }

  create(request: RdvRequest): Observable<RdvResponse> {
    return this.http.post<RdvResponse>(this.apiUrl, request);
  }

  update(id: number, request: RdvRequest): Observable<RdvResponse> {
    return this.http.put<RdvResponse>(`${this.apiUrl}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  cancel(id: number): Observable<RdvResponse> {
    return this.http.patch<RdvResponse>(`${this.apiUrl}/${id}/cancel`, {});
  }
}
