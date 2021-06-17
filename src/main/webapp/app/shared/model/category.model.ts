import { ITransaction } from '@/shared/model/transaction.model';
import { IUser } from '@/shared/model/user.model';

export interface ICategory {
  id?: number;
  name?: string;
  color?: string | null;
  showStatistics?: boolean | null;
  transactions?: ITransaction[] | null;
  user?: IUser | null;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public name?: string,
    public color?: string | null,
    public showStatistics?: boolean | null,
    public transactions?: ITransaction[] | null,
    public user?: IUser | null
  ) {
    this.showStatistics = this.showStatistics ?? false;
  }
}
