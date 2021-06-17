<template>
  <div>
    <PieChart v-if="expensesSources.length != 0" :chart-data="chartData" :options="options" />
    <div v-else>No data</div>
  </div>
</template>
<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransaction } from '@/shared/model/transaction.model';
import PieChart from './PieChart.vue';

import TransactionService from '@/entities/transaction/transaction.service';

@Component({
  mixins: [Vue2Filters.mixin],
  components: {
    PieChart,
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
export default class ExpensesSources extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;
  private removeId: number = null;

  public transactions: ITransaction[] = [];

  public isFetching = false;
  public options = {
    responsive: true,
    maintainAspectRatio: false,
    cutoutPercentage: 70,
    tooltips: {
      callbacks: {
        label: function (tooltipItem, data) {
          //get the concerned dataset
          var dataset = data.datasets[tooltipItem.datasetIndex];
          //calculate the total of this data set
          var total = dataset.data.reduce(function (previousValue, currentValue, currentIndex, array) {
            return previousValue + currentValue;
          });
          //get the current items value
          var currentValue = dataset.data[tooltipItem.index];
          //calculate the precentage based on the total and current item, also this does a rough rounding to give a whole number
          var percentage = Math.floor((currentValue / total) * 100 + 0.5);

          return percentage + '%';
        },
      },
    },
  };

  public chartData = null;

  public expensesSources = [];

  public mounted(): void {
    this.retrieveAllTransactions();
  }

  public clear(): void {
    this.retrieveAllTransactions();
  }

  public retrieveAllTransactions(): void {
    this.isFetching = true;

    this.transactionService()
      .retrieveExpensesSources(this.year)
      .then(
        res => {
          this.expensesSources = res.data.map(el => el.amount);
          this.chartData = {
            labels: res.data.map(el => el.category),
            datasets: [
              {
                borderWidth: 0,
                backgroundColor: res.data.map(el => el.color),
                data: this.expensesSources,
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
