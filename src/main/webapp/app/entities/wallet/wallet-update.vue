<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="financeApp.wallet.home.createOrEditLabel" data-cy="WalletCreateUpdateHeading">Create or edit a Wallet</h2>
        <div>
          <div class="form-group">
            <label class="form-control-label" for="wallet-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="wallet-name"
              data-cy="name"
              :class="{ valid: !$v.wallet.name.$invalid, invalid: $v.wallet.name.$invalid }"
              v-model="$v.wallet.name.$model"
              required
            />
            <div v-if="$v.wallet.name.$anyDirty && $v.wallet.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.wallet.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="wallet-currency">Currency</label>
            <select
              class="form-control"
              name="currency"
              :class="{ valid: !$v.wallet.currency.$invalid, invalid: $v.wallet.currency.$invalid }"
              v-model="$v.wallet.currency.$model"
              id="wallet-currency"
              data-cy="currency"
              required
            >
              <option value="EUR">EUR</option>
              <option value="USD">USD</option>
              <option value="GBP">GBP</option>
            </select>
            <div v-if="$v.wallet.currency.$anyDirty && $v.wallet.currency.$invalid">
              <small class="form-text text-danger" v-if="!$v.wallet.currency.required"> This field is required. </small>
            </div>
          </div>
          <div class="">
            <label class="form-control-label" for="wallet-countTotal"
              >Count in Total
              <input
                type="checkbox"
                class="form-check"
                name="countTotal"
                id="wallet-countTotal"
                data-cy="countTotal"
                :class="{ valid: !$v.wallet.countTotal.$invalid, invalid: $v.wallet.countTotal.$invalid }"
                v-model="$v.wallet.countTotal.$model"
              />
            </label>
          </div>
          <div class="form-group flex">
            <label class="form-control-label mr-2" for="wallet-color">Color </label>
            <v-swatches
              type="text"
              class="align-middle"
              name="color"
              id="wallet-color"
              data-cy="color"
              :class="{ valid: !$v.wallet.color.$invalid, invalid: $v.wallet.color.$invalid }"
              v-model="$v.wallet.color.$model"
              show-fallback
              :swatches="swatches"
              row-length="5"
              popover-x="left"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="wallet-order">Order</label>
            <input
              type="number"
              class="form-control"
              name="order"
              id="wallet-order"
              data-cy="order"
              :class="{ valid: !$v.wallet.order.$invalid, invalid: $v.wallet.order.$invalid }"
              v-model.number="$v.wallet.order.$model"
            />
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
            :disabled="$v.wallet.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./wallet-update.component.ts"></script>
