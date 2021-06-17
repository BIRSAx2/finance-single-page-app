<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="financeApp.category.home.createOrEditLabel" data-cy="CategoryCreateUpdateHeading">Create or edit a Category</h2>
        <div>
          <div class="form-group">
            <label class="form-control-label" for="category-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="category-name"
              data-cy="name"
              :class="{ valid: !$v.category.name.$invalid, invalid: $v.category.name.$invalid }"
              v-model="$v.category.name.$model"
              required
            />
            <div v-if="$v.category.name.$anyDirty && $v.category.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.category.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label mr-2" for="category-color">Color</label>
            <v-swatches
              type="text"
              class="align-middle"
              name="color"
              id="category-color"
              data-cy="color"
              :class="{ valid: !$v.category.color.$invalid, invalid: $v.category.color.$invalid }"
              v-model="$v.category.color.$model"
              show-fallback
              :swatches="swatches"
              row-length="5"
              popover-x="left"
            />
          </div>
          <div class="form-group">
            <input
              type="checkbox"
              class="mr-2 bg-primary"
              style="transform: scale(1.5)"
              name="showStatistics"
              id="category-showStatistics"
              data-cy="showStatistics"
              :class="{ valid: !$v.category.showStatistics.$invalid, invalid: $v.category.showStatistics.$invalid }"
              v-model="$v.category.showStatistics.$model"
            />
            <label class="form-control-label" for="category-showStatistics"> Show Statistics </label>
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
            :disabled="$v.category.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
