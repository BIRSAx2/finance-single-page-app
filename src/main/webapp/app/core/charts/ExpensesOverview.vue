<template>
  <div>
    <LineChart :chart-data="chartData" :options="options" />
  </div>
</template>
<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransaction } from '@/shared/model/transaction.model';
import LineChart from './LineChart.vue';
import hexAlpha from 'hex-alpha';

import TransactionService from '@/entities/transaction/transaction.service';

@Component({
  mixins: [Vue2Filters.mixin],
  components: {
    LineChart,
  },
  props: {
    year: Number,
  },
  watch: {
    year: function (newVal, oldVal) {
      // watch it
      this.retrieveAllTransactions();
    },
  },
})
export default class ExpensesOverview extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;
  private removeId: number = null;

  public transactions: ITransaction[] = [];

  public isFetching = false;
  public options = { responsive: true, maintainAspectRatio: false };

  public chartData = null;
  public gradient = null;
  public gradient2 = null;

  public incomes = [];
  public expenses = [];

  public mounted(): void {
    this.retrieveAllTransactions();
  }

  public clear(): void {
    this.retrieveAllTransactions();
  }

  public retrieveAllTransactions(): void {
    this.isFetching = true;

    this.transactionService()
      .retrieveExpensesOverview(this.year)
      .then(
        res => {
          this.expenses = Object.values(res.data);
          this.incomes = Object.values(res.data);

          this.chartData = {
            labels: Object.keys(res.data),
            datasets: [
              {
                label: 'Expenses',
                borderColor: '#f55c47',
                pointBackgroundColor: '#f55c47',
                pointBorderColor: '#f55c47',
                borderWidth: 1,
                backgroundColor: hexAlpha('#f55c47', 0.3),
                data: this.incomes,
              },
            ],
          };
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
}
</script>
