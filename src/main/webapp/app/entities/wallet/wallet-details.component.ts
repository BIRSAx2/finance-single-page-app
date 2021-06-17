import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWallet } from '@/shared/model/wallet.model';
import WalletService from './wallet.service';

@Component
export default class WalletDetails extends Vue {
  @Inject('walletService') private walletService: () => WalletService;
  public wallet: IWallet = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.walletId) {
        vm.retrieveWallet(to.params.walletId);
      }
    });
  }

  public retrieveWallet(walletId) {
    this.walletService()
      .find(walletId)
      .then(res => {
        this.wallet = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
