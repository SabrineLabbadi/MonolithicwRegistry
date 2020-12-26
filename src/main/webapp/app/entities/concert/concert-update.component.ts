import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConcert, Concert } from 'app/shared/model/concert.model';
import { ConcertService } from './concert.service';

@Component({
  selector: 'jhi-concert-update',
  templateUrl: './concert-update.component.html',
})
export class ConcertUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    date: [],
    address: [],
  });

  constructor(protected concertService: ConcertService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concert }) => {
      if (!concert.id) {
        const today = moment().startOf('day');
        concert.date = today;
      }

      this.updateForm(concert);
    });
  }

  updateForm(concert: IConcert): void {
    this.editForm.patchValue({
      id: concert.id,
      name: concert.name,
      date: concert.date ? concert.date.format(DATE_TIME_FORMAT) : null,
      address: concert.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concert = this.createFromForm();
    if (concert.id !== undefined) {
      this.subscribeToSaveResponse(this.concertService.update(concert));
    } else {
      this.subscribeToSaveResponse(this.concertService.create(concert));
    }
  }

  private createFromForm(): IConcert {
    return {
      ...new Concert(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcert>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
