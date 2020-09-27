<template>
  <transition name="modal">
    <div id="overlay" v-show="showContent" @click="closeModal">
      <div class="modal-container" @click="stopEvent">
        <h1>確認</h1>
        <p>この見積を削除しますか？</p>

        <div class="ui horizontal divider">削除対象</div>

        <div style="padding-bottom: 4em;" class="ui stackable one column grid">
          <div class="ui column">
            <table id="tasks" class="ui celled compact table">
              <thead>
                <tr>
                  <th>
                    <i class="calendar plus icon"></i>見積番号
                  </th>
                  <th>
                    <i class="info circle icon"></i>案件名
                  </th>
                  <th>
                    <i class="info circle icon"></i>顧客名
                  </th>
                  <th>
                    <i class="info circle icon"></i>担当者名
                  </th>
                  <th>
                    <i class="info circle icon"></i>予算金額
                  </th>
                  <th>
                    <i class="info circle icon"></i>見積金額
                  </th>
                </tr>
              </thead>
              <tr>
                <td>{{ estimate.id }}</td>
                <td>{{ estimate.name }}</td>
                <td>{{ customer.name }}</td>
                <td>{{ employee.name }}</td>
                <td>{{ estimate.budgetedAmount | priceFormat }}</td>
                <td>{{ estimate.amount | priceFormat }}</td>
              </tr>
            </table>
          </div>
        </div>

        <div class="ui stackable one column grid">
          <div class="ui column">
            <button class="positive ui button" @click="onRemove">削除</button>
            <button class="ui button" @click="closeModal">キャンセル</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import Vue from "vue";
import { api } from "../helpers/Helpers";
export default {
  props: {
    // estimateDetail: {
    //   type: Object,
    //   required: false,
    //   default: () => {
    //     return {
    //       subId: "1",
    //       estimateId: "",
    //       productCd: "",
    //       productName: "",
    //       productPrice: "",
    //       quantity: "",
    //       amount: ""
    //     };
    //   }
    // }
  },
  data() {
    return {
      showContent: false,
      estimate: {},
      customer: {},
      employee: {}
    };
  },
  filters: {
    priceFormat: function(value) {
      if (!value) return "";
      return "¥" + value.toLocaleString();
    }
  },
  methods: {
    onRemove: function() {
      this.$parent.onExecRemoveEstimate(this.estimate);
      this.showContent = false;
    },
    openEstimateDeletelModal(estimate) {
      let tmp = Vue.util.extend({}, estimate);
      this.estimate = tmp;
      this.getCustomerByCd()
      this.getEmployeeByCd()

      this.showContent = true;
    },
    closeModal: function() {
      this.showContent = false;
    },
    stopEvent: function() {
      event.stopPropagation();
    },
    async getCustomerByCd() {
      this.customer = await api.getCustomersByCd(this.estimate.customerCd);
    },
    async getEmployeeByCd() {
      this.employee = await api.getEmployeesByCd(this.estimate.employeeCd);
    }
  }
};
</script>