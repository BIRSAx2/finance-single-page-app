<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="transaction">
        <h2 class="jh-entity-heading" data-cy="transactionDetailsHeading"><span>Transaction</span> {{ transaction.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Wallet</span>
          </dt>
          <dd>
            <div v-if="transaction.wallet">
              <router-link :to="{ name: 'WalletView', params: { walletId: transaction.wallet.id } }">{{
                transaction.wallet.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <div v-if="transaction.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: transaction.category.id } }">{{
                transaction.category.name || '-'
              }}</router-link>
            </div>
            <div v-else>-</div>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ transaction.description || '-' }}</span>
          </dd>
          <dt>
            <span>Amount</span>
          </dt>
          <dd>
            <span>{{ transaction.amount }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span :class="{ 'text-success': transaction.type == 'INCOME', 'text-danger': transaction.type == 'EXPENSE' }">{{
              transaction.type
            }}</span>
          </dd>
          <dt>
            <span>Date</span>
          </dt>
          <dd>
            <span>{{ transaction.date | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="transaction.id"
          :to="{ name: 'TransactionEdit', params: { transactionId: transaction.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./transaction-details.component.ts"></script>
