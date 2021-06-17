import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWallet } from '@/shared/model/wallet.model';

import WalletService from './wallet.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Wallet extends Vue {
  @Inject('walletService') private walletService: () => WalletService;
  private removeId: number = null;

  public wallets: IWallet[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllWallets();
  }

  public clear(): void {
    this.retrieveAllWallets();
  }

  public retrieveAllWallets(): void {
    this.isFetching = true;

    this.walletService()
      .retrieve()
      .then(
        res => {
          this.wallets = res.data;
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

  public prepareRemove(instance: IWallet): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeWallet(): void {
    this.walletService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Wallet is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllWallets();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
