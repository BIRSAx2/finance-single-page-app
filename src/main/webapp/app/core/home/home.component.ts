import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import EarningExpenseCard from '@/core/charts/EarningExpensesCard.vue';
import IncomeOverview from '@/core/charts/IncomeOverview.vue';
import RevenueSources from '@/core/charts/RevenueSources.vue';
import ExpensesSources from '@/core/charts/ExpensesSources.vue';
import ExpensesOverview from '@/core/charts/ExpensesOverview.vue';
import CookieLaw from 'vue-cookie-law';
// @ts-ignore
import VueNumberInput from '@chenfengyuan/vue-number-input';

@Component({
  components: {
    EarningCard: EarningExpenseCard,
    IncomeOverview,
    RevenueSources,
    ExpensesSources,
    ExpensesOverview,
    VueNumberInput,
    CookieLaw,
  },
})
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  public earningOverviewYear = new Date().getFullYear();
  public revenueSourcesYear = new Date().getFullYear();
  public expensesOverviewYear = new Date().getFullYear();
  public expensesSourcesYear = new Date().getFullYear();

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
