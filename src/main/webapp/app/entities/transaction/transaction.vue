<template>
  <div>
    <h2 id="page-heading" data-cy="TransactionHeading">
      <span id="transaction-heading">Transactions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TransactionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-transaction"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Transaction </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && transactions && transactions.length === 0">
      <span>No transactions found</span>
    </div>
    <div class="table-responsive" v-if="transactions && transactions.length > 0">
      <table class="table table-striped" aria-describedby="transactions">
        <thead>
          <tr>
            <th scope="row"><span>Wallet</span></th>
            <th scope="row"><span>Category</span></th>
            <th scope="row"><span>Amount</span></th>
            <th scope="row"><span>Type</span></th>
            <th scope="row"><span>Date</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transaction in transactions" :key="transaction.id" data-cy="entityTable">
            <td>
              <div v-if="transaction.wallet">
                <router-link :to="{ name: 'WalletView', params: { walletId: transaction.wallet.id } }"
                  >{{ transaction.wallet.name }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="transaction.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: transaction.category.id } }"
                  >{{ transaction.category.name }}
                </router-link>
              </div>
              <div v-else>-</div>
            </td>
            <td>{{ transaction.amount }}</td>
            <td :class="{ 'text-success': transaction.type == 'INCOME', 'text-danger': transaction.type == 'EXPENSE' }">
              {{ transaction.type }}
            </td>
            <td>{{ transaction.date | formatDate }}</td>
            <td>{{ transaction.description || '-' }}</td>

            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TransactionView', params: { transactionId: transaction.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TransactionEdit', params: { transactionId: transaction.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(transaction)"
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
        ><span id="financeApp.transaction.delete.question" data-cy="transactionDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="uaa-delete-transaction-heading">Are you sure you want to delete this Transaction?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="uaa-confirm-delete-transaction"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTransaction()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./transaction.component.ts"></script>
