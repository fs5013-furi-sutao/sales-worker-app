import Vue from 'vue';
import Router from 'vue-router';
import Tasks from './views/Tasks.vue';
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
      redirect: '/tasks'
    },
    {
      path: '/tasks',
      name: 'tasks',
      component: Tasks
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
      path: '/tasks/:id/edit',
      name: 'edit',
      component: Edit
    }
   
  ]
});