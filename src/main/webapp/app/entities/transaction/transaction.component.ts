import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransaction } from '@/shared/model/transaction.model';

import TransactionService from './transaction.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Transaction extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;
  private removeId: number = null;

  public transactions: ITransaction[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTransactions();
  }

  public clear(): void {
    this.retrieveAllTransactions();
  }

  public retrieveAllTransactions(): void {
    this.isFetching = true;

    this.transactionService()
      .retrieve()
      .then(
        res => {
          this.transactions = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITransaction): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTransaction(): void {
    this.transactionService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Transaction is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTransactions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
