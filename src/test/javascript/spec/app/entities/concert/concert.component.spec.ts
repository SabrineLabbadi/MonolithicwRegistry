import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MonolithicwRegistryTestModule } from '../../../test.module';
import { ConcertComponent } from 'app/entities/concert/concert.component';
import { ConcertService } from 'app/entities/concert/concert.service';
import { Concert } from 'app/shared/model/concert.model';

describe('Component Tests', () => {
  describe('Concert Management Component', () => {
    let comp: ConcertComponent;
    let fixture: ComponentFixture<ConcertComponent>;
    let service: ConcertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonolithicwRegistryTestModule],
        declarations: [ConcertComponent],
      })
        .overrideTemplate(ConcertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConcertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Concert(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.concerts && comp.concerts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
