import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MonolithicwRegistryTestModule } from '../../../test.module';
import { ConcertDetailComponent } from 'app/entities/concert/concert-detail.component';
import { Concert } from 'app/shared/model/concert.model';

describe('Component Tests', () => {
  describe('Concert Management Detail Component', () => {
    let comp: ConcertDetailComponent;
    let fixture: ComponentFixture<ConcertDetailComponent>;
    const route = ({ data: of({ concert: new Concert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonolithicwRegistryTestModule],
        declarations: [ConcertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConcertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConcertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load concert on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.concert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
