<template>
  <div>
    <h2 id="page-heading" data-cy="CategoryHeading">
      <span id="category-heading">Categories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'CategoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-category"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Category </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && categories && categories.length === 0">
      <span>No categories found</span>
    </div>
    <div class="table-responsive" v-if="categories && categories.length > 0">
      <table class="table table-striped" aria-describedby="categories">
        <thead>
          <tr>
            <th scope="row" class="text-center"><span>Name</span></th>
            <th scope="row" class="text-center"><span>Color</span></th>
            <th scope="row" class="text-center"><span>Show statistics</span></th>
            <th scope="row" class="text-center"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="category in categories" :key="category.id" data-cy="entityTable">
            <td class="text-left pl-5">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: category.id } }">{{ category.name }} </router-link>
            </td>
            <td class="text-center">
              <div class="mx-auto" style="width: 100px; height: 22px" v-bind:style="{ backgroundColor: category.color }"></div>
            </td>
            <td class="text-center">
              <font-awesome-icon icon="check" v-if="category.showStatistics" class="text-success" />
              <font-awesome-icon icon="times" v-else class="text-danger" />
            </td>

            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: category.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CategoryEdit', params: { categoryId: category.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(category)"
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
        ><span id="financeApp.category.delete.question" data-cy="categoryDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="uaa-delete-category-heading">Are you sure you want to delete this Category?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="uaa-confirm-delete-category"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeCategory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./category.component.ts"></script>
