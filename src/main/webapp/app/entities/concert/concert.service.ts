import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConcert } from 'app/shared/model/concert.model';

type EntityResponseType = HttpResponse<IConcert>;
type EntityArrayResponseType = HttpResponse<IConcert[]>;

@Injectable({ providedIn: 'root' })
export class ConcertService {
  public resourceUrl = SERVER_API_URL + 'api/concerts';

  constructor(protected http: HttpClient) {}

  create(concert: IConcert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concert);
    return this.http
      .post<IConcert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(concert: IConcert): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(concert);
    return this.http
      .put<IConcert>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConcert>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConcert[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(concert: IConcert): IConcert {
    const copy: IConcert = Object.assign({}, concert, {
      date: concert.date && concert.date.isValid() ? concert.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((concert: IConcert) => {
        concert.date = concert.date ? moment(concert.date) : undefined;
      });
    }
    return res;
  }
}
