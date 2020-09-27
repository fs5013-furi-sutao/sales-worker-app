<template>
  <transition name="modal">
    <div id="overlay" v-show="showContent" @click="closeModal">
      <div class="modal-container" @click="stopEvent">
        <h1>確認</h1>
        <p>この明細を削除しますか？</p>

        <div class="ui horizontal divider">削除対象</div>

        <div style="padding-bottom: 4em;" class="ui stackable one column grid">
          <div class="ui column">
            <table id="tasks" class="ui celled compact table">
              <thead>
                <tr>
                  <th>
                    <i class="calendar plus icon"></i>枝番
                  </th>
                  <th>
                    <i class="info circle icon"></i>商品名
                  </th>
                  <th>
                    <i class="info circle icon"></i>単価
                  </th>
                  <th>
                    <i class="info circle icon"></i>数量
                  </th>
                  <th>
                    <i class="info circle icon"></i>金額
                  </th>
                </tr>
              </thead>
              <tr>
                <td>{{ estimateDetail.subId }}</td>
                <td>{{ estimateDetail.productName }}</td>
                <td>{{ estimateDetail.productPrice }}</td>
                <td>{{ estimateDetail.quantity }}</td>
                <td>{{ estimateDetail.amount }}</td>
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
    estimateDetail: {
      type: Object,
      required: false,
      default: () => {
        return {
          subId: "1",
          estimateId: "",
          productCd: "",
          productName: "",
          productPrice: "",
          quantity: "",
          amount: ""
        };
      }
    }
  },
  data() {
    return {
      showContent: false
    };
  },
  methods: {
    onRemove: function() {
      this.$parent.onExecRemoveDetail(this.estimateDetail);
      this.showContent = false;
    },
    openEstimateDetailModal: function(estimateDetail) {
      let tmp = Vue.util.extend({}, estimateDetail);

      this.estimateDetail = tmp;
      this.showContent = true;
    },
    closeModal: function() {
      this.showContent = false;
    },
    stopEvent: function() {
      event.stopPropagation();
    },
    onDestroy: function(product) {
      this.$parent.product.cd = product.cd;
      this.$parent.product.name = product.name;
      this.$parent.product.price = product.price;
      this.showContent = false;
    },
    async searchProducts() {
      this.products = await api.getProductsByName(this.search.name);
      this.products = this.products.products;
      this.showContent = true;
    }
  }
};
</script>