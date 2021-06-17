<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="financeApp.transaction.home.createOrEditLabel" data-cy="TransactionCreateUpdateHeading">Create or edit a Transaction</h2>
        <div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="transaction-description"
              data-cy="description"
              :class="{ valid: !$v.transaction.description.$invalid, invalid: $v.transaction.description.$invalid }"
              v-model="$v.transaction.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-amount">Amount</label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="transaction-amount"
              data-cy="amount"
              :class="{ valid: !$v.transaction.amount.$invalid, invalid: $v.transaction.amount.$invalid }"
              v-model.number="$v.transaction.amount.$model"
              required
            />
            <div v-if="$v.transaction.amount.$anyDirty && $v.transaction.amount.$invalid">
              <small class="form-text text-danger" v-if="!$v.transaction.amount.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.transaction.amount.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.transaction.type.$invalid, invalid: $v.transaction.type.$invalid }"
              v-model="$v.transaction.type.$model"
              id="transaction-type"
              data-cy="type"
              required
            >
              <option value="INCOME">INCOME</option>
              <option value="EXPENSE">EXPENSE</option>
            </select>
            <div v-if="$v.transaction.type.$anyDirty && $v.transaction.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.transaction.type.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-date">Date</label>
            <div class="d-flex">
              <input
                id="transaction-date"
                data-cy="date"
                type="datetime-local"
                class="form-control"
                name="date"
                :class="{ valid: !$v.transaction.date.$invalid, invalid: $v.transaction.date.$invalid }"
                required
                :value="convertDateTimeFromServer($v.transaction.date.$model)"
                @change="updateInstantField('date', $event)"
              />
            </div>
            <div v-if="$v.transaction.date.$anyDirty && $v.transaction.date.$invalid">
              <small class="form-text text-danger" v-if="!$v.transaction.date.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.transaction.date.ZonedDateTimelocal">
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-wallet">Wallet</label>
            <select class="form-control" id="transaction-wallet" data-cy="wallet" name="wallet" v-model="transaction.wallet" required>
              <option v-if="!transaction.wallet" v-bind:value="null" selected></option>
              <option
                v-bind:value="transaction.wallet && walletOption.id === transaction.wallet.id ? transaction.wallet : walletOption"
                v-for="walletOption in wallets"
                :key="walletOption.id"
              >
                {{ walletOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.transaction.wallet.$anyDirty && $v.transaction.wallet.$invalid">
            <small class="form-text text-danger" v-if="!$v.transaction.wallet.required"> This field is required. </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transaction-category">Category</label>
            <select
              class="form-control"
              id="transaction-category"
              data-cy="category"
              name="category"
              v-model="transaction.category"
              required
            >
              <option v-if="!transaction.category" v-bind:value="null" selected></option>
              <option
                v-bind:value="transaction.category && categoryOption.id === transaction.category.id ? transaction.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.name }}
              </option>
            </select>
          </div>
          <div v-if="$v.transaction.category.$anyDirty && $v.transaction.category.$invalid">
            <small class="form-text text-danger" v-if="!$v.transaction.category.required"> This field is required. </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.transaction.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./transaction-update.component.ts"></script>
