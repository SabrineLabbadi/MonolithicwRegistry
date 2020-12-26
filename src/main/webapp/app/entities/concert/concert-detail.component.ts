import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcert } from 'app/shared/model/concert.model';

@Component({
  selector: 'jhi-concert-detail',
  templateUrl: './concert-detail.component.html',
})
export class ConcertDetailComponent implements OnInit {
  concert: IConcert | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concert }) => (this.concert = concert));
  }

  previousState(): void {
    window.history.back();
  }
}
