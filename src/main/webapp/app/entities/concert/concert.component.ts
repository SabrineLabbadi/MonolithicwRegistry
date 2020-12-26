import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConcert } from 'app/shared/model/concert.model';
import { ConcertService } from './concert.service';
import { ConcertDeleteDialogComponent } from './concert-delete-dialog.component';

@Component({
  selector: 'jhi-concert',
  templateUrl: './concert.component.html',
})
export class ConcertComponent implements OnInit, OnDestroy {
  concerts?: IConcert[];
  eventSubscriber?: Subscription;

  constructor(protected concertService: ConcertService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.concertService.query().subscribe((res: HttpResponse<IConcert[]>) => (this.concerts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConcerts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConcert): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConcerts(): void {
    this.eventSubscriber = this.eventManager.subscribe('concertListModification', () => this.loadAll());
  }

  delete(concert: IConcert): void {
    const modalRef = this.modalService.open(ConcertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.concert = concert;
  }
}
