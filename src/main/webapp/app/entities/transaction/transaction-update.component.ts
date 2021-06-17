import { Component, Vue, Inject } from 'vue-property-decorator';

import { decimal, required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import WalletService from '@/entities/wallet/wallet.service';
import { IWallet } from '@/shared/model/wallet.model';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import { ITransaction, Transaction } from '@/shared/model/transaction.model';
import TransactionService from './transaction.service';

const validations: any = {
  transaction: {
    description: {},
    amount: {
      required,
      decimal,
    },
    type: {
      required,
    },
    date: {
      required,
    },
    wallet: {
      required,
    },
    category: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TransactionUpdate extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;
  public transaction: ITransaction = new Transaction();

  @Inject('walletService') private walletService: () => WalletService;

  public wallets: IWallet[] = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transactionId) {
        vm.retrieveTransaction(to.params.transactionId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.transaction.id) {
      this.transactionService()
        .update(this.transaction)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Transaction is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.transactionService()
        .create(this.transaction)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Transaction is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.transaction[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.transaction[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.transaction[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.transaction[field] = null;
    }
  }

  public retrieveTransaction(transactionId): void {
    this.transactionService()
      .find(transactionId)
      .then(res => {
        res.date = new Date(res.date);
        this.transaction = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.walletService()
      .retrieve()
      .then(res => {
        this.wallets = res.data;
      });
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
  }
}
