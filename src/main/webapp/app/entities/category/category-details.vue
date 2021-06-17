<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="category">
        <h2 class="jh-entity-heading" data-cy="categoryDetailsHeading">
          Category: <span class="font-weight-bold">{{ category.name }}</span>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Color</span>
          </dt>
          <dd>
            <div style="width: 22px; height: 22px" v-bind:style="{ backgroundColor: category.color }"></div>
          </dd>
          <dt>
            <span>Show Statistics</span>
          </dt>
          <dd>
            <span>
              <font-awesome-icon icon="check" v-if="category.showStatistics" class="text-success" />
              <font-awesome-icon icon="times" v-else class="text-danger"
            /></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="category.id" :to="{ name: 'CategoryEdit', params: { categoryId: category.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./category-details.component.ts"></script>
