export interface IBand {
  id?: number;
  name?: string;
  address?: string;
  style?: string;
  concertName?: string;
  concertId?: number;
}

export class Band implements IBand {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public style?: string,
    public concertName?: string,
    public concertId?: number
  ) {}
}
