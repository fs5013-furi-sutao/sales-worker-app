<template>
  <div>
    <!-- Spring Data JPAで動的にクエリを生成するQuery by Example機能 -->
    <!-- https://dawaan.com/spring-data-jpa-qbe/ -->
    <h1>見積検索</h1>

    <p>検索条件に部分一致した見積情報が結果に表示されます。</p>
    <div class="ui horizontal divider">検索条件</div>
    <br />
    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>見積番号</div>
          <input
            type="text"
            v-model="searchCondition.estimateId"
            @click="onSearchById"
          />
          <button
            class="primary ui button mleft fixsizebutton"
            @click="onSearchById"
          >
            検索
          </button>
        </div>
      </div>
      <div class="ui column"></div>
    </div>

    <div class="ui stackable one column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>案件名</div>
          <input type="text" v-model="searchCondition.estimateName" />
          <button
            class="primary ui button mleft fixsizebutton"
            @click="onSearchByName"
          >
            検索
          </button>
        </div>
      </div>
    </div>

    <div class="ui stackable two column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積ステータス
          </div>
          <select
            style="max-width: 17em"
            class="ui dropdown fluid"
            v-model="searchCondition.estimateStatus"
          >
            <option value hidden></option>
            <option
              syle="color: black;"
              v-for="status in statusOptions"
              :key="status.id"
              :value="status.id"
            >
              {{ status.name }}
            </option>
          </select>
          <button
            class="primary ui button mleft fixsizebutton"
            @click="onSearchByStatus"
          >
            検索
          </button>
        </div>
      </div>
      <div class="ui column"></div>
    </div>

    <div class="ui stackable one column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>顧客名</div>
          <input
            class="readonly"
            type="text"
            v-model="customer.name"
            readonly
          />
          <button
            class="positive ui button mleft fixsizebutton"
            @click="openCustomerSearchModal"
          >
            顧客選択
          </button>
          <button
            class="ui button mleft fixsizebutton"
            @click="onClearCustomer"
          >
            クリア
          </button>
          <button
            class="primary ui button mleft fixsizebutton"
            @click="onSearchByCustomer"
          >
            検索
          </button>
        </div>
      </div>
    </div>

    <div class="ui stackable one column grid">
      <div class="ui column">
        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>担当者名</div>
          <input
            class="readonly"
            type="text"
            v-model="employee.name"
            readonly
          />
          <button
            class="positive ui button mleft fixsizebutton"
            @click="openEmployeeSearchModal"
          >
            担当者選択
          </button>
          <button
            class="ui button mleft fixsizebutton"
            @click="onClearEmployee"
          >
            クリア
          </button>
          <button
            class="primary ui button mleft fixsizebutton"
            @click="onSearchByEmployee"
          >
            検索
          </button>
        </div>
      </div>
      <!-- <div class="ui column"></div> -->
    </div>

    <div class="ui horizontal divider">検索結果一覧</div>

    <table id="tasks" class="ui celled compact table">
      <thead>
        <tr>
          <th><i class="calendar plus icon"></i>見積番号</th>
          <th><i class="info circle icon"></i>案件名</th>
          <th><i class="info circle icon"></i>ステータス</th>
          <th><i class="info circle icon"></i>顧客名</th>
          <th><i class="info circle icon"></i>担当者名</th>
          <th><i class="info circle icon"></i>予算金額</th>
          <th><i class="info circle icon"></i>見積金額</th>
          <th><i class="lock open icon"></i></th>
          <th><i class="edit icon"></i></th>

          <!--th colspan="3"></th-->
        </tr>
      </thead>
      <tr v-for="(estimate, i) in estimates" :key="i">
        <td>{{ estimate.id }}</td>
        <td>{{ estimate.name }}</td>
        <td>{{ estimate.status }}</td>
        <td>{{ estimate.customerName }}</td>
        <td>{{ estimate.employeeName }}</td>
        <td>{{ estimate.budgetedAmount | priceFormat }}</td>
        <td>{{ estimate.amount | priceFormat }}</td>
        <td width="75" class="center aligned">
          <router-link
            :to="{ name: 'show-estimate', params: { id: estimate.id } }"
            >詳細</router-link
          >
        </td>
        <td width="75" class="center aligned">
          <router-link :to="{ name: 'edit', params: { id: estimate.id } }"
            >編集</router-link
          >
        </td>
      </tr>
    </table>
    <p v-if="errorNoResults" class="error">検索結果がありません</p>
    <p v-if="errorNoInput" class="error">検索条件に入力がありません</p>
    <CustomerSearchModal ref="customerSearchModal" />
    <EmployeeSearchModal ref="employeeSearchModal" />
  </div>
</template>

<script>
import { api } from "../helpers/Helpers";
import CustomerSearchModal from "@/components/CustomerSearchModal";
import EmployeeSearchModal from "@/components/EmployeeSearchModal";
export default {
  name: "estimates",
  components: {
    CustomerSearchModal,
    EmployeeSearchModal,
  },
  props: {
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
  },
  data() {
    return {
      tasks: [],
      maxCd: [],
      searchCondition: {
        estimateId: "",
        estimateName: "",
        estimateStatus: "",
      },
      estimateJson: [],
      estimate: {
        id: "",
        name: "",
        status: "",
        customerName: "",
        employeeName: "",
        budgetedAmount: "",
        amount: "",
      },
      estimates: [],
      customer: {
        cd: "",
        name: "",
      },
      employee: {
        cd: "",
        name: "",
      },
      errorNoResults: false,
      errorNoInput: false,
    };
  },
  filters: {
    priceFormat: function (value) {
      if (!value) return "";
      return "¥" + value.toLocaleString();
    },
  },
  methods: {
    isNoSearchCondition: function (value) {
      this.errorNoResults = false;
      if (value === "") {
        this.errorNoInput = true;
        return true;
      }
      this.errorNoInput = false;
      return false;
    },
    isNoSearchResults: function (value) {
      this.errorNoInput = false;
      if (value == 0) {
        this.errorNoResults = true;
        return true;
      }
      this.errorNoResults = false;
      return false;
    },
    async onSearchById() {
      this.estimates = [];
      if (this.isNoSearchCondition(this.searchCondition.estimateId)) return;

      this.estimateJson = await api.getEstimatesByLikeId(
        this.searchCondition.estimateId
      );
      if (this.isNoSearchResults(this.estimateJson)) return;

      for (const [key, value] of this.estimateJson.entries()) {
        console.log(key);
        let customer = await api.getCustomersByCd(value["customerCd"]);
        let employee = await api.getEmployeesByCd(value["employeeCd"]);

        this.estimates.push({
          id: value["id"],
          name: value["name"],
          customerName: customer.name,
          employeeName: employee.name,
          budgetedAmount: value["budgetedAmount"],
          amount: value["amount"],
        });
      }
    },
    async onSearchByName() {
      this.estimates = [];
      if (this.isNoSearchCondition(this.searchCondition.estimateName)) return;

      this.estimateJson = await api.getEstimatesByLikeName(
        this.searchCondition.estimateName
      );
      if (this.isNoSearchResults(this.estimateJson)) return;

      for (const [key, value] of this.estimateJson.entries()) {
        console.log(key);
        let customer = await api.getCustomersByCd(value["customerCd"]);
        let employee = await api.getEmployeesByCd(value["employeeCd"]);

        this.estimates.push({
          id: value["id"],
          name: value["name"],
          customerName: customer.name,
          employeeName: employee.name,
          budgetedAmount: value["budgetedAmount"],
          amount: value["amount"],
        });
      }
    },
    async onSearchByStatus() {
      this.estimates = [];
      if (this.isNoSearchCondition(this.searchCondition.estimateStatus)) return;

      this.estimateJson = await api.getEstimatesByLikeStatus(
        this.searchCondition.estimateStatus
      );
      if (this.isNoSearchResults(this.estimateJson)) return;

      for (const [key, value] of this.estimateJson.entries()) {
        console.log(key);
        let customer = await api.getCustomersByCd(value["customerCd"]);
        let employee = await api.getEmployeesByCd(value["employeeCd"]);

        this.estimates.push({
          id: value["id"],
          name: value["name"],
          customerName: customer.name,
          employeeName: employee.name,
          budgetedAmount: value["budgetedAmount"],
          amount: value["amount"],
        });
      }
    },
    async onSearchByCustomer() {
      this.estimates = [];
      if (this.isNoSearchCondition(this.customer.name)) return;

      this.estimateJson = await api.getEstimatesByCustomerCd(this.customer.cd);
      if (this.isNoSearchResults(this.estimateJson)) return;

      for (const [key, value] of this.estimateJson.entries()) {
        console.log(key);
        let customer = await api.getCustomersByCd(value["customerCd"]);
        let employee = await api.getEmployeesByCd(value["employeeCd"]);

        this.estimates.push({
          id: value["id"],
          name: value["name"],
          customerName: customer.name,
          employeeName: employee.name,
          budgetedAmount: value["budgetedAmount"],
          amount: value["amount"],
        });
      }
    },
    async onSearchByEmployee() {
      this.estimates = [];
      if (this.isNoSearchCondition(this.employee.name)) return;

      this.estimateJson = await api.getEstimatesByCustomerCd(this.employee.cd);
      if (this.isNoSearchResults(this.estimateJson)) return;

      for (const [key, value] of this.estimateJson.entries()) {
        console.log(key);
        let customer = await api.getCustomersByCd(value["customerCd"]);
        let employee = await api.getEmployeesByCd(value["employeeCd"]);

        this.estimates.push({
          id: value["id"],
          name: value["name"],
          customerName: customer.name,
          employeeName: employee.name,
          budgetedAmount: value["budgetedAmount"],
          amount: value["amount"],
        });
      }
    },
    onClearCustomer: function () {
      this.customer.cd = "";
      this.customer.name = "";
    },
    onClearEmployee: function () {
      this.employee.cd = "";
      this.employee.name = "";
    },
    async onDestroy(id) {
      const sure = window.confirm("Are you sure?");
      if (!sure) return;
      await api.deletetask(id);
      this.flash("task deleted sucessfully!", "success");
      const newtasks = this.tasks.filter((task) => task._id !== id);
      this.tasks = newtasks;
    },
    openCustomerSearchModal: function () {
      this.$refs.customerSearchModal.openModal();
    },
    openEmployeeSearchModal: function () {
      this.$refs.employeeSearchModal.openModal();
    },
  },
  async mounted() {
    this.tasks = await api.gettasks();
    this.maxCd = await api.getMaxCd();
  },
};
</script>

<style scoped>
.error {
  color: red;
}
</style>