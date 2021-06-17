import { ITransaction } from '@/shared/model/transaction.model';
import { IUser } from '@/shared/model/user.model';

import { Currency } from '@/shared/model/enumerations/currency.model';
export interface IWallet {
  id?: number;
  name?: string;
  currency?: Currency;
  countTotal?: boolean | null;
  color?: string | null;
  order?: number | null;
  transactions?: ITransaction[] | null;
  user?: IUser | null;
}

export class Wallet implements IWallet {
  constructor(
    public id?: number,
    public name?: string,
    public currency?: Currency,
    public countTotal?: boolean | null,
    public color?: string | null,
    public order?: number | null,
    public transactions?: ITransaction[] | null,
    public user?: IUser | null
  ) {
    this.countTotal = this.countTotal ?? false;
  }
}
