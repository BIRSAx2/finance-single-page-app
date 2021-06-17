<template>
  <div>
    <h2 id="page-heading" data-cy="WalletHeading">
      <span id="wallet-heading">Wallets</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'WalletCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-wallet"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Wallet </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && wallets && wallets.length === 0">
      <span>No wallets found</span>
    </div>
    <div class="table-responsive" v-if="wallets && wallets.length > 0">
      <table class="table table-striped" aria-describedby="wallets">
        <thead>
          <tr>
            <th scope="row" class="text-center"><span>Name</span></th>
            <th scope="row" class="text-center"><span>Currency</span></th>
            <th scope="row" class="text-center"><span>Count in total</span></th>
            <th scope="row" class="text-center"><span>Color</span></th>
            <th scope="row" class="text-center"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="wallet in wallets" :key="wallet.id" data-cy="entityTable" class="text-center">
            <td class="text-center">
              <router-link :to="{ name: 'WalletView', params: { walletId: wallet.id } }">{{ wallet.name }}</router-link>
            </td>
            <td>
              <h5>{{ { EUR: '€', USD: '$', GBP: '£' }[wallet.currency] }}</h5>
            </td>
            <td class="text-center">
              <font-awesome-icon icon="check" v-if="wallet.countTotal" class="text-success" />
              <font-awesome-icon icon="times" v-else class="text-danger" />
            </td>
            <td class="text-center">
              <div class="mx-auto" style="width: 100px; height: 22px" v-bind:style="{ backgroundColor: wallet.color }"></div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'WalletView', params: { walletId: wallet.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'WalletEdit', params: { walletId: wallet.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(wallet)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="financeApp.wallet.delete.question" data-cy="walletDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="uaa-delete-wallet-heading">Are you sure you want to delete this Wallet?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="uaa-confirm-delete-wallet"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeWallet()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./wallet.component.ts"></script>
