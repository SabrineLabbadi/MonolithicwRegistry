import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBand, Band } from 'app/shared/model/band.model';
import { BandService } from './band.service';
import { IConcert } from 'app/shared/model/concert.model';
import { ConcertService } from 'app/entities/concert/concert.service';

@Component({
  selector: 'jhi-band-update',
  templateUrl: './band-update.component.html',
})
export class BandUpdateComponent implements OnInit {
  isSaving = false;
  concerts: IConcert[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    style: [],
    concertId: [],
  });

  constructor(
    protected bandService: BandService,
    protected concertService: ConcertService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ band }) => {
      this.updateForm(band);

      this.concertService.query().subscribe((res: HttpResponse<IConcert[]>) => (this.concerts = res.body || []));
    });
  }

  updateForm(band: IBand): void {
    this.editForm.patchValue({
      id: band.id,
      name: band.name,
      address: band.address,
      style: band.style,
      concertId: band.concertId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const band = this.createFromForm();
    if (band.id !== undefined) {
      this.subscribeToSaveResponse(this.bandService.update(band));
    } else {
      this.subscribeToSaveResponse(this.bandService.create(band));
    }
  }

  private createFromForm(): IBand {
    return {
      ...new Band(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      style: this.editForm.get(['style'])!.value,
      concertId: this.editForm.get(['concertId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBand>>): void {
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

  trackById(index: number, item: IConcert): any {
    return item.id;
  }
}
