import { Moment } from 'moment';
import { IBand } from 'app/shared/model/band.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IConcert {
  id?: number;
  name?: string;
  date?: Moment;
  address?: string;
  bands?: IBand[];
  customers?: ICustomer[];
}

export class Concert implements IConcert {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public address?: string,
    public bands?: IBand[],
    public customers?: ICustomer[]
  ) {}
}
