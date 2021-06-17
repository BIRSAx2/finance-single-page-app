<template>
  <div class="container-fluid">
    <div class="row" v-if="!authenticated">
      <div class="row flex">
        <div class="alert alert-warning row mx-auto">
          Please login to access the application.
          <span>You don't have an account yet?</span>&nbsp;
          <router-link class="alert-link" to="/register">Register a new account</router-link>
        </div>
        <img src="/content/images/wallet.svg" alt="" class="img-fluid rounded flex mx-auto w" />
      </div>
    </div>
    <div class="d-flex flex-column" v-else>
      <div class="col-12">
        <h1 class="display-4 text-center">Welcome, {{ username.charAt(0).toUpperCase() + username.slice(1) }}</h1>
      </div>
      <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
      </div>
      <!-- Content Row -->
      <div class="row">
        <div class="col-xl-3 col-md-6 mb-4">
          <EarningCard transactionType="INCOME" timePeriod="YEAR" :periodValue="new Date().getFullYear()" />
        </div>

        <div class="col-xl-3 col-md-6 mb-4">
          <EarningCard transactionType="INCOME" timePeriod="MONTH" :periodValue="new Date().getMonth() + 1" />
        </div>
        <div class="col-xl-3 col-md-6 mb-4">
          <EarningCard transactionType="EXPENSE" timePeriod="YEAR" :periodValue="new Date().getFullYear()" />
        </div>
        <div class="col-xl-3 col-md-6 mb-4">
          <EarningCard transactionType="EXPENSE" timePeriod="MONTH" :periodValue="new Date().getMonth() + 1" />
        </div>
      </div>

      <!-- Content Row -->

      <div class="row">
        <!-- Area Chart -->
        <div class="col-xl-8 col-lg-7">
          <div class="card shadow mb-4">
            <!-- Card Header - Dropdown -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">Earnings Overview</h6>
              <vue-number-input v-model="earningOverviewYear" :min="1990" inline controls></vue-number-input>
            </div>
            <!-- Card Body -->
            <div class="card-body">
              <div class="chart-area">
                <IncomeOverview :year="this.earningOverviewYear" />
              </div>
            </div>
          </div>
        </div>

        <!-- Pie Chart -->
        <div class="col-xl-4 col-lg-5">
          <div class="card shadow mb-4">
            <!-- Card Header - Dropdown -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">Revenue Sources</h6>
              <vue-number-input v-model="revenueSourcesYear" :min="1990" inline controls></vue-number-input>
            </div>
            <!-- Card Body -->
            <div class="card-body">
              <div class="chart-pie pt-4 pb-2">
                <RevenueSources :year="this.revenueSourcesYear" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <!-- Area Chart -->
        <div class="col-xl-8 col-lg-7">
          <div class="card shadow mb-4">
            <!-- Card Header - Dropdown -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">Expenses Overview</h6>
              <vue-number-input v-model="expensesOverviewYear" :min="1990" inline controls></vue-number-input>
            </div>
            <!-- Card Body -->
            <div class="card-body">
              <div class="chart-area">
                <ExpensesOverview :year="this.expensesOverviewYear" />
              </div>
            </div>
          </div>
        </div>

        <!-- Pie Chart -->
        <div class="col-xl-4 col-lg-5">
          <div class="card shadow mb-4">
            <!-- Card Header - Dropdown -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">Expenses Sources</h6>
              <vue-number-input v-model="expensesSourcesYear" :min="1990" inline controls></vue-number-input>
            </div>
            <!-- Card Body -->
            <div class="card-body">
              <div class="chart-pie pt-4 pb-2">
                <ExpensesSources :year="this.expensesSourcesYear" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
