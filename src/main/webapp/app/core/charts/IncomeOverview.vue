<template>
  <div>
    <LineChart :chart-data="chartData" :options="options" />
  </div>
</template>
<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import LineChart from './LineChart.vue';

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
export default class IncomeOverview extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;

  public isFetching = false;
  public options = { responsive: true, maintainAspectRatio: false };

  public chartData = null;

  public incomes = [];

  public mounted(): void {
    this.retrieveAllTransactions();
  }

  public clear(): void {
    this.retrieveAllTransactions();
  }

  public retrieveAllTransactions(): void {
    this.isFetching = true;

    this.transactionService()
      .retrieveStatsPerYear(this.year)
      .then(
        res => {
          this.expenses = Object.values(res.data);
          this.incomes = Object.values(res.data);

          this.chartData = {
            labels: Object.keys(res.data),
            datasets: [
              {
                label: 'Income',
                borderColor: '#05CBE1',
                pointBackgroundColor: '#05CBE1',
                pointBorderColor: '#05CBE1',
                borderWidth: 1,
                backgroundColor: 'rgba(5, 203, 225,0.3)',
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
