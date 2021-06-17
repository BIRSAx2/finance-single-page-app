<template>
  <div class="card shadow h-100 py-2">
    <div class="card-body border-left border-5 border-primary">
      <div class="row no-gutters align-items-center">
        <div class="col mr-2">
          <div
            class="text-xs font-weight-bold text-uppercase mb-1"
            :class="{ 'text-success': this.transactionType == 'INCOME', 'text-danger': this.transactionType == 'EXPENSE' }"
          >
            {{ transactionType == 'INCOME' ? 'Earnings' : 'Expenses' }}
            ({{ this.timePeriod == 'MONTH' ? 'This Month' : 'This Year' }})
          </div>
          <div class="h5 mb-0 font-weight-bold text-gray-800">$ {{ this.earnings }}</div>
        </div>
        <div class="col-auto">
          <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { Component, Inject, Prop, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import TransactionService from '@/entities/transaction/transaction.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class EarningExpenseCard extends Vue {
  @Inject('transactionService') private transactionService: () => TransactionService;
  @Prop({ required: true }) readonly transactionType: string;
  @Prop({ required: true }) readonly timePeriod: string;
  @Prop({ required: true }) readonly periodValue: number;
  public earnings: number = 0.0;

  public mounted(): void {
    this.transactionService()
      .retrieveSumByPeriod(this.transactionType, this.timePeriod, this.periodValue)
      .then(res => {
        this.earnings = res;
      });
  }
}
</script>
