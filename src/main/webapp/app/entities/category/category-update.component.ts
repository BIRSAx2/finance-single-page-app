import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import TransactionService from '@/entities/transaction/transaction.service';
import { ITransaction } from '@/shared/model/transaction.model';

import UserService from '@/admin/user-management/user-management.service';

import { ICategory, Category } from '@/shared/model/category.model';
import CategoryService from './category.service';

import VSwatches from 'vue-swatches';

// Import the styles too, globally
import 'vue-swatches/dist/vue-swatches.css';

const validations: any = {
  category: {
    name: {
      required,
    },
    color: {},
    showStatistics: {},
  },
};

@Component({
  validations,
  components: { VSwatches },
})
export default class CategoryUpdate extends Vue {
  @Inject('categoryService') private categoryService: () => CategoryService;
  public category: ICategory = new Category();

  @Inject('transactionService') private transactionService: () => TransactionService;

  public swatches = [
    ['#F64272', '#F6648B', '#F493A7', '#F891A6', '#FFCCD5'],
    ['#8b5aff', '#a27bff', '#b99cff', '#d0bdff', '#e8deff'],
    ['#51e5db', '#74ebe3', '#96f0ea', '#b9f5f1', '#dcfaf8'],
    ['#ffa51a', '#ffb748', '#ffc976', '#ffdba3', '#ffedd1'],
  ];
  public transactions: ITransaction[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categoryId) {
        vm.retrieveCategory(to.params.categoryId);
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
    if (this.category.id) {
      this.categoryService()
        .update(this.category)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Category is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.categoryService()
        .create(this.category)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Category is created with identifier ' + param.id;
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

  public retrieveCategory(categoryId): void {
    this.categoryService()
      .find(categoryId)
      .then(res => {
        this.category = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.transactionService()
      .retrieve()
      .then(res => {
        this.transactions = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
