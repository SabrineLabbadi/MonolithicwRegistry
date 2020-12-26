import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConcert } from 'app/shared/model/concert.model';
import { ConcertService } from './concert.service';

@Component({
  templateUrl: './concert-delete-dialog.component.html',
})
export class ConcertDeleteDialogComponent {
  concert?: IConcert;

  constructor(protected concertService: ConcertService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.concertService.delete(id).subscribe(() => {
      this.eventManager.broadcast('concertListModification');
      this.activeModal.close();
    });
  }
}
