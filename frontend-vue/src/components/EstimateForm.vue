<template>
  <form action="#" @submit.prevent>
    <p v-if="errorsPresent" class="error">Please fill out both fields!</p>

    <div class="ui stackable two column grid">
      <div class="ui column"></div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>見積番号</div>
          <input
            class="readonly"
            type="text"
            placeholder="自動採番"
            v-model="estimate.id"
            readonly
          />
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積案件名
          </div>
          <input
            type="text"
            placeholder="例）新規オフィス（伏見）への移転に伴う機器・用品調達"
            v-model="estimate.name"
          />
        </div>
      </div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積ステータス
          </div>
          <select
            class="ui dropdown fluid"
            :style="{ color: selectedColor }"
            v-model="estimate.status"
            @change="updateValue"
            @click="onClickSelect"
            @blur="onBlurSelect"
          >
            <option value hidden>ステータスを選択してください</option>
            <option
              syle="color: black;"
              v-for="status in statusOptions"
              :key="status.id"
              :value="status.id"
            >
              {{ status.name }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 顧客名</div>
          <input
            class="readonly"
            type="text"
            placeholder="顧客を選択してください"
            v-model="customer.name"
            readonly
          />
          <button
            class="primary ui button mleft fixsizebutton"
            @click="openCustomerSearchModal"
          >
            顧客検索
          </button>
        </div>
      </div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 担当者名</div>
          <input
            class="readonly"
            type="text"
            placeholder="担当従業員を選択してください"
            v-model="employee.name"
            readonly
          />
          <button
            class="primary ui button mleft fixsizebutton"
            @click="openEmployeeSearchModal"
          >
            担当者検索
          </button>
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 予算金額</div>
          <input
            type="text"
            placeholder="予算金額を入力してください"
            v-model="estimate.budgetedAmount"
            @keyup="calcBudgetOver"
          />
        </div>
      </div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="info circle icon"></i> 予算超過額
          </div>
          <input
            class="readonly"
            type="text"
            placeholder="予算金額を入力してください"
            v-bind:value="estimate.budgetOver | priceFormat"
            readonly
          />
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 合計金額</div>
          <input
            class="readonly"
            type="text"
            placeholder="見積商品を追加してください"
            v-bind:value="estimate.amount | priceFormat"
            readonly
          />
        </div>
      </div>
    </div>

    <div class="ui horizontal divider">追加商品</div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="info circle icon"></i> 商品コード
          </div>
          <input
            class="readonly"
            type="text"
            placeholder="商品を選択してください"
            v-model="product.cd"
            readonly
          />
          <button
            class="primary ui button mleft fixsizebutton"
            @click="openModal"
          >
            商品検索
          </button>
        </div>
      </div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 商品名</div>
          <input
            class="readonly"
            type="text"
            placeholder="商品を選択してください"
            v-model="product.name"
            readonly
          />
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 単価</div>
          <input
            class="readonly"
            type="text"
            placeholder="商品を選択してください"
            v-bind:value="product.price | priceFormat"
            readonly
          />
          <button class="ui button mleft fixsizebutton" @click="onClearProduct">
            商品をクリア
          </button>
        </div>
      </div>
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 数量</div>
          <input
            type="text"
            placeholder="数量を入力してください"
            v-model="product.quantity"
            @keyup="onProductQuantityChange"
          />
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 金額</div>
          <input
            class="readonly"
            type="text"
            v-bind:value="product.amount | priceFormat"
            readonly
          />
        </div>
      </div>
    </div>

    <div class="ui stackable one column grid">
      <div class="ui column">
        <button class="positive ui button" @click="addDetail">
          明細に追加
        </button>
      </div>
    </div>

    <div class="ui divider"></div>

    <div class="ui stackable one column grid">
      <div class="ui column">
        <button class="huge orange ui button" @click="createOrUpdate">
          {{ this.buttonName }}
        </button>
      </div>
    </div>

    <div class="ui horizontal divider">見積明細</div>

    <div style="padding-bottom: 4em" class="ui stackable one column grid">
      <div class="ui column">
        <table class="ui celled compact table">
          <thead>
            <tr>
              <th><i class="calendar plus icon"></i>枝番</th>
              <th><i class="info circle icon"></i>商品名</th>
              <th><i class="info circle icon"></i>単価</th>
              <th><i class="info circle icon"></i>数量</th>
              <th><i class="info circle icon"></i>金額</th>
              <th>
                <i class="edit icon"></i>
              </th>
            </tr>
          </thead>
          <tr v-for="(estimateDetail, i) in estimateDetails" :key="i">
            <td>{{ estimateDetail.subId }}</td>
            <td>{{ estimateDetail.productName }}</td>
            <td>{{ estimateDetail.productPrice | priceFormat }}</td>
            <td>{{ estimateDetail.quantity }}</td>
            <td>{{ estimateDetail.amount | priceFormat }}</td>
            <td class="center aligned" @click="onDetailRemove(estimateDetail)">
              <a style="cursor: pointer">削除</a>
            </td>
          </tr>
        </table>
      </div>
    </div>

    <ProductSearchModal ref="modal" />
    <CustomerSearchModal ref="customerSearchModal" />
    <EmployeeSearchModal ref="employeeSearchModal" />
    <EstimateDetailEditModal ref="estimateDetailModal" />
  </form>
</template>

<script>
import Vue from "vue";
import { api } from "../helpers/Helpers";
import CustomerSearchModal from "@/components/CustomerSearchModal";
import EmployeeSearchModal from "@/components/EmployeeSearchModal";
import ProductSearchModal from "@/components/ProductSearchModal";
import EstimateDetailEditModal from "@/components/EstimateDetailEditModal";
export default {
  components: {
    CustomerSearchModal,
    EmployeeSearchModal,
    ProductSearchModal,
    EstimateDetailEditModal,
  },
  name: "estimate-form",
  props: {
    buttonName: {
      type: String,
      required: false,
      //   default: () => {
      //     return "見積編集";
      //   }
    },
    nextDetailSubId: {
      type: Number,
      required: false,
      default: () => {
        return 1;
      },
    },
    statusOptions: {
      type: Array,
      required: false,
      default: () => {
        return [
          {
            id: "1",
            name: "1: 見積中",
          },
          {
            id: "2",
            name: "2: 見積完了",
          },
          {
            id: "3",
            name: "3: 受注済",
          },
        ];
      },
    },
    estimate: {
      type: Object,
      required: false,
      default: () => {
        return {
          id: "",
          name: "",
          status: "",
          customerCd: "",
          employeeCd: "",
          budgetedAmount: "",
          budgetOver: "",
          amount: "",
        };
      },
    },
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
          amount: "",
        };
      },
    },
    estimateDetails: {
      type: Array,
      required: false,
      default: () => {
        return [];
      },
    },
    // task: {
    //   type: Object,
    //   required: false,
    //   default: () => {
    //     return {
    //       task1: "",
    //       task2: "",
    //     };
    //   },
    // },
    product: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: "",
          price: "",
          quantity: "",
          amount: "",
        };
      },
    },
    customer: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: "",
        };
      },
    },
    employee: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: "",
        };
      },
    },
  },
  data() {
    return {
      errorsPresent: false,
      selectedValue: "",
      selectedColor: "#bbb",

      //   estimateDetails: []
    };
  },
  filters: {
    priceFormat: function (value) {
      if (!value) return "";
      return "¥" + value.toLocaleString();
    },
  },
  methods: {
    calcBudgetOver: function () {
      this.estimate.budgetOver =
        this.estimate.amount - this.estimate.budgetedAmount;
    },
    calcEatimateAmount: function () {
      let sum = 0;
      for (const [key, value] of this.estimateDetails.entries()) {
        console.log(key);
        sum += value["amount"];
      }
      sum += this.product.amount;
      this.estimate.amount = sum;
    },

    onProductQuantityChange: function () {
      this.product.amount = this.product.price * this.product.quantity;
      this.calcEatimateAmount();
      this.calcBudgetOver();
    },
    async onExecRemoveDetail(estimateDetail) {
      for (const [key, value] of this.estimateDetails.entries()) {
        if (value["subId"] === estimateDetail["subId"]) {
          delete this.estimateDetails.splice(key, 1);
        }
      }

      if (estimateDetail.estimateId === "") return;
      await api.deleteEatimateDetail(estimateDetail.id);
    },
    onDetailRemove: function (estimateDetail) {
      this.$refs.estimateDetailModal.openEstimateDetailModal(estimateDetail);
    },
    isDetailCancel(statusId) {
      if (statusId == 1) return "#eee";
      if (statusId == 2) return "blue";
    },
    convertToDetailStatusName(statusId) {
      let target;
      for (let status of this.detailStatus) {
        if (status["id"] === statusId) {
          target = status;
        }
      }
      return target["name"];
    },
    async createOrUpdate() {
      this.$emit("createOrUpdate", this.estimate, this.estimateDetails);
    },
    updateValue: function () {
      if (this.estimate.status !== "") {
        this.selectedColor = "black";
      }
    },
    onClickSelect: function () {
      this.selectedColor = "black";
    },
    onBlurSelect: function () {
      if (this.estimate.status !== "") {
        this.selectedColor = "black";
        return;
      }
      this.selectedColor = "#bbb";
    },
    async addDetail() {
      let estimateDetailTmp = Vue.util.extend({}, this.estimateDetail);
      let tmp = Vue.util.extend({}, this.product);
      estimateDetailTmp.estimateId = this.estimate.id;
      estimateDetailTmp.subId = this.nextDetailSubId;
      estimateDetailTmp.productCd = tmp.cd;
      estimateDetailTmp.productName = tmp.name;
      estimateDetailTmp.productPrice = tmp.price;
      estimateDetailTmp.quantity = tmp.quantity;
      estimateDetailTmp.amount = tmp.amount;

      this.estimateDetails.push(estimateDetailTmp);
      this.product.cd = "";
      this.product.name = "";
      this.product.price = "";
      this.product.quantity = "";
      this.product.amount = "";

      this.nextDetailSubId++;

      if (estimateDetailTmp.estimateId === "") return;
      await api.createEatimateDetail(estimateDetailTmp);
    },
    onClearProduct: function () {
      this.product.cd = "";
      this.product.name = "";
      this.product.price = "";
      this.product.quantity = "";
      this.product.amount = "";
    },
    getChild(name) {
      for (let child of this.$children)
        if (child.$options.name == name) return child;
    },
    openModal: function () {
      this.$refs.modal.openModal();
    },
    openCustomerSearchModal: function () {
      this.$refs.customerSearchModal.openModal();
    },
    openEmployeeSearchModal: function () {
      this.$refs.employeeSearchModal.openModal();
    },
  },
};
</script>

<style scoped>
.error {
  color: red;
}
</style>