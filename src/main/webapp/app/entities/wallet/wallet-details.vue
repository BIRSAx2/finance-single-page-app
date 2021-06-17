<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="wallet">
        <h2 class="jh-entity-heading" data-cy="walletDetailsHeading">
          Wallet: <span class="font-weight-bold">{{ wallet.name }}</span>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Currency</span>
          </dt>
          <dd>
            <span>{{ wallet.currency }}</span>
          </dd>
          <dt>
            <span>Count in total</span>
          </dt>
          <dd>
            <font-awesome-icon icon="check" v-if="wallet.countTotal" class="text-success" />
            <font-awesome-icon icon="times" v-else class="text-danger" />
          </dd>
          <dt>
            <span>Color</span>
          </dt>
          <dd>
            <div style="width: 22px; height: 22px" v-bind:style="{ backgroundColor: wallet.color }"></div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="wallet.id" :to="{ name: 'WalletEdit', params: { walletId: wallet.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./wallet-details.component.ts"></script>
