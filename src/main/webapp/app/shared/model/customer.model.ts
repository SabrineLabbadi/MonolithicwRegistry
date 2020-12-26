import { Moment } from 'moment';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  dob?: Moment;
  address?: string;
  concertName?: string;
  concertId?: number;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public dob?: Moment,
    public address?: string,
    public concertName?: string,
    public concertId?: number
  ) {}
}
