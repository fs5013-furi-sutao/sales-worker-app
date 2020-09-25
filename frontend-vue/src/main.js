import Vue from 'vue'
import App from './App.vue'

import 'semantic-ui-css/semantic.css';
import router from './Router'

// Create a crud Application using Vue, Node and MongoDB
// https://codesource.io/create-a-crud-application-using-vue-node-and-mongodb/

// https://www.geeksforgeeks.org/semantic-ui-buttons/

// https://bezkoder.com/spring-boot-pagination-filter-jpa-pageable/
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')