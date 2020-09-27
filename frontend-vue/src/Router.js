import Vue from 'vue';
import Router from 'vue-router';
import Estimates from './views/Estimates.vue';
import New from './views/New.vue';
import Show from './views/Show.vue';
import Edit from './views/Edit.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'active',
  routes: [
    {
      path: '/',
      redirect: '/estimates'
    },
    {
      path: '/estimates',
      name: 'estimates',
      component: Estimates
    },
    {
      path: '/estimates/new',
      name: 'new-estimate',
      component: New
    },
    {
      path: '/estimates/:id',
      name: 'show-estimate',
      component: Show
    },
    {
      path: '/estimates/:id/edit',
      name: 'edit',
      component: Edit
    }
   
  ]
});