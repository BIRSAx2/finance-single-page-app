<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <navbar></navbar>
    </div>
    <div class="container-fluid">
      <div class="card jh-card">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <span data-cy="loginTitle" slot="modal-title" id="login-title">Sign in</span>
        <login-form></login-form>
      </b-modal>

      <footer>
        <cookie-law theme="dark">
          <div slot="message">
            We use Cookies and similar tracking technologies to track the activity on Our Service and store certain information. Tracking
            technologies used are beacons, tags, and scripts to collect and track information and to improve and analyze Our Service.
            <br /><router-link to="privacy">Click here</router-link> to see out privacy policy.
          </div>
        </cookie-law>
      </footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
