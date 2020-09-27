<template>
  <transition name="modal">
    <div id="overlay" v-show="showContent" @click="closeModal">
      <div class="modal-container" @click="stopEvent">
        <h1>顧客検索</h1>
        <p>名前を入力してください。</p>

        <div class="ui labeled input fluid">
          <div class="ui label long">
            <i class="info circle icon"></i> 会社・個人名（部分一致）
          </div>
          <input type="text" placeholder="Enter Details" v-model="search.name" />
          <button class="primary ui button mleft" @click="searchCustomers">検索</button>
        </div>
        <div class="ui horizontal divider">検索結果</div>

        <table id="tasks" class="ui celled compact table">
          <thead>
            <tr>
              <th>
                <i class="calendar plus icon"></i>顧客コード
              </th>
              <th>
                <i class="info circle icon"></i>顧客名
              </th>
              <th>
                <i class="edit icon"></i>
              </th>
            </tr>
          </thead>
          <tr v-for="(customer, i) in customers" :key="i">
            <td>{{ customer.cd }}</td>
            <td>{{ customer.name }}</td>
            <td width="75" class="center aligned" @click.prevent="onChoice(customer)">
              <a style="cursor: pointer;">選択</a>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </transition>
</template>

<script>
import { api } from "../helpers/Helpers";
export default {
  props: {
    search: {
      type: Object,
      required: false,
      default: () => {
        return {
          name: ""
        };
      }
    },
    customer: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: ""
        };
      }
    }
  },
  data() {
    return {
      customers: [],
      showContent: false
    };
  },
  methods: {
    openModal: function() {
      this.showContent = true;
    },
    closeModal: function() {
      this.showContent = false;
    },
    stopEvent: function() {
      event.stopPropagation();
    },
    onChoice: function(customer) {
      this.$parent.customer.cd = customer.cd;
      this.$parent.customer.name = customer.name;
      this.$parent.estimate.customerCd = customer.cd;
      this.showContent = false;
    },
    async searchCustomers() {
      this.customers = await api.getCustomersByName(this.search.name);
      this.customers = this.customers.customers;
      this.showContent = true;
    }
  }
};
</script>