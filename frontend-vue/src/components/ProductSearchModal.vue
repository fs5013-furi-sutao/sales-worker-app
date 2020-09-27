<template>
  <transition name="modal">
    <div id="overlay" v-show="showContent" @click="closeModal">
      <div class="modal-container" @click="stopEvent">
        <h1>商品検索</h1>
        <p>商品名を入力してください。</p>

        <div class="ui labeled input fluid">
          <div class="ui label long">
            <i class="info circle icon"></i> 商品名（部分一致）
          </div>
          <input type="text" placeholder="Enter Details" v-model="search.name" />
          <button class="primary ui button mleft" @click="searchProducts()">検索</button>
        </div>
        <div class="ui horizontal divider">検索結果</div>

        <table id="tasks" class="ui celled compact table">
          <thead>
            <tr>
              <th>
                <i class="calendar plus icon"></i>Task
              </th>
              <th>
                <i class="info circle icon"></i>Detail
              </th>
              <th>
                <i class="info circle icon"></i>Detail
              </th>
              <th>
                <i class="edit icon"></i>
              </th>
            </tr>
          </thead>
          <tr v-for="(product, i) in products" :key="i">
            <td>{{ product.cd }}</td>
            <td>{{ product.name }}</td>
            <td>{{ product.price }}</td>
            <td width="75" class="center aligned" @click.prevent="onDestroy(product)">
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
    product: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: "",
          price: ""
        };
      }
    }
  },
  data() {
    return {
      products: [],
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