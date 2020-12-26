import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MonolithicwRegistryTestModule } from '../../../test.module';
import { ConcertUpdateComponent } from 'app/entities/concert/concert-update.component';
import { ConcertService } from 'app/entities/concert/concert.service';
import { Concert } from 'app/shared/model/concert.model';

describe('Component Tests', () => {
  describe('Concert Management Update Component', () => {
    let comp: ConcertUpdateComponent;
    let fixture: ComponentFixture<ConcertUpdateComponent>;
    let service: ConcertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MonolithicwRegistryTestModule],
        declarations: [ConcertUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConcertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConcertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Concert(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Concert();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
