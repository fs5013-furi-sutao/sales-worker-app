<template>
  <div>
    <h1>見積詳細</h1>

    <form action="#" @submit.prevent>
      <div class="ui stackable two column grid">
        <div class="ui column"></div>
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積番号
            </div>
            <input class="readonly" type="text" v-model="estimate.id" readonly />
          </div>
        </div>
      </div>

      <div class="ui stackable two column grid">
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積案件名
            </div>
            <input class="readonly" type="text" v-model="estimate.name" readonly />
          </div>
        </div>
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積ステータス
            </div>
            <select class="ui dropdown fluid readonly" v-model="estimate.status" disabled>
              <option value hidden></option>
              <option
                v-for="status in statusOptions"
                :key="status.id"
                :value="status.id"
              >{{ status.name }}</option>
            </select>
          </div>
        </div>
      </div>

      <div class="ui stackable two column grid">
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="info circle icon"></i> 顧客名
            </div>
            <input class="readonly" type="text" v-model="customer.name" readonly />
          </div>
        </div>
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="info circle icon"></i> 担当者名
            </div>
            <input class="readonly" type="text" v-model="employee.name" readonly />
          </div>
        </div>
      </div>

      <div class="ui stackable two column grid">
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="info circle icon"></i> 予算金額
            </div>
            <input
              class="readonly"
              type="text"
              v-bind:value="estimate.budgetedAmount | priceFormat"
              readonly
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
              v-bind:value="estimate.budgetOver | priceFormat"
              readonly
            />
          </div>
        </div>
      </div>

      <div class="ui stackable two column grid">
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="info circle icon"></i> 合計金額
            </div>
            <input
              class="readonly"
              type="text"
              v-bind:value="estimate.amount | priceFormat"
              readonly
            />
          </div>
        </div>
      </div>

      <div class="ui stackable one column grid">
        <div class="ui column">
          <button class="positive ui button" @click="onEdit">編集</button>
          <button class="ui button" @click="onDetailRemove">削除</button>
        </div>
      </div>

      <div class="ui horizontal divider">見積明細</div>

      <div style="padding-bottom: 4em;" class="ui stackable one column grid">
        <div class="ui column">
          <table class="ui celled compact table">
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
            <tr v-for="(estimateDetail, i) in estimateDetails" :key="i">
              <td>{{ estimateDetail.subId }}</td>
              <td>{{ estimateDetail.productName }}</td>
              <td>{{ estimateDetail.productPrice | priceFormat }}</td>
              <td>{{ estimateDetail.quantity }}</td>
              <td>{{ estimateDetail.amount | priceFormat }}</td>
            </tr>
          </table>
        </div>
      </div>
      <EstimateDeleteModal ref="estimateDeleteModal" />
    </form>

  </div>
</template>

<script>
import { api } from "../helpers/Helpers";
import EstimateDeleteModal from "@/components/EstimateDeleteModal";
export default {
  name: "show",
  components: {
    EstimateDeleteModal
  },
  props: {
    // estimate: {
    //   type: Object,
    //   required: false,
    //   default: () => {
    //     return {
    //       id: "1",
    //       name: "",
    //       status: "",
    //       customerCd: "",
    //       employeeCd: "",
    //       budgetedAmount: "",
    //       budgetOver: "",
    //       amount: ""
    //     };
    //   }
    // },
    statusOptions: {
      type: Array,
      required: false,
      default: () => {
        return [
          {
            id: "1",
            name: "1: 見積中"
          },
          {
            id: "2",
            name: "2: 見積完了"
          },
          {
            id: "3",
            name: "3: 受注済"
          }
        ];
      }
    }
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
    // customer: {
    //   type: Object,
    //   required: false,
    //   default: () => {
    //     return {
    //       cd: "",
    //       name: ""
    //     };
    //   }
    // },
    // employee: {
    //   type: Object,
    //   required: false,
    //   default: () => {
    //     return {
    //       cd: "",
    //       name: ""
    //     };
    //   }
    // }
  },
  data() {
    return {
      estimate: {},
      estimateDetails: [],
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
  async mounted() {
    this.estimate = await api.getEstimate(this.$route.params.id);
    this.customer = await api.getCustomersByCd(this.estimate.customerCd);
    this.employee = await api.getEmployeesByCd(this.estimate.employeeCd);
    this.calcBudgetOver();
    let estimateDetailTmp = await api.getEstimateDetailsByEstimateId(
      this.estimate.id
    );

    for (const [key, value] of estimateDetailTmp.entries()) {
      console.log(key);
      let productCd = value["productCd"];
      let product = await api.getProductByCd(productCd);

      this.estimateDetails.push({
        id: value["id"],
        subId: value["subId"],
        estimateId: value["estimateId"],
        productName: product.name,
        productPrice: product.price,
        quantity: value["quantity"],
        amount: product.price * value["quantity"]
      });
    }
  },
  methods: {
    async onExecRemoveEstimate(estimate) {
      await api.deleteEstimateDetailsByEstimateId(estimate.id);
      await api.deleteEatimate(estimate.id);
      this.flash("estimate deleted", "success");
      this.$router.push(`/estimates`);
      //   this.$router.push(`/estimates/${resEstimate.id}`);
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
    onDetailRemove: function() {
      this.$refs.estimateDeleteModal.openEstimateDeletelModal(this.estimate);
    },
    onEdit: function() {
      this.$router
        .push({ name: "edit", params: { id: this.$route.params.id } })
        .catch(() => {});
    },
    calcBudgetOver: function() {
      this.estimate.budgetOver =
        this.estimate.amount - this.estimate.budgetedAmount;
    }
  }
};
</script>

<style scoped>
.actions a {
  display: block;
  text-decoration: underline;
  margin: 20px 10px;
}
</style>