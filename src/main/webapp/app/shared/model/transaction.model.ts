import { IWallet } from '@/shared/model/wallet.model';
import { ICategory } from '@/shared/model/category.model';

import { TransactionType } from '@/shared/model/enumerations/transaction-type.model';
export interface ITransaction {
  id?: number;
  description?: string | null;
  amount?: number;
  type?: TransactionType;
  date?: Date;
  wallet?: IWallet;
  category?: ICategory;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public description?: string | null,
    public amount?: number,
    public type?: TransactionType,
    public date?: Date,
    public wallet?: IWallet,
    public category?: ICategory
  ) {}
}
