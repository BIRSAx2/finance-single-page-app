import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import MyFooter from '@/core/footer/MyFooter.vue';
import Navbar from '@/core/navbar/navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';
import CookieLaw from 'vue-cookie-law';
import '@/shared/config/dayjs';

@Component({
  components: {
    ribbon: Ribbon,
    navbar: Navbar,
    'login-form': LoginForm,
    MyFooter,
    CookieLaw,
  },
})
export default class App extends Vue {}
