<template>
  <transition name="modal">
    <div id="overlay" v-show="showContent" @click="closeModal">
      <div class="modal-container" @click="stopEvent">
        <h1>担当者検索</h1>
        <p>名前を入力してください。</p>

        <div class="ui labeled input fluid">
          <div class="ui label long">
            <i class="info circle icon"></i> 従業員名（部分一致）
          </div>
          <input type="text" placeholder="Enter Details" v-model="search.name" />
          <button class="primary ui button mleft" @click="searchCustomers">検索</button>
        </div>
        <div class="ui horizontal divider">検索結果</div>

        <table id="tasks" class="ui celled compact table">
          <thead>
            <tr>
              <th>
                <i class="calendar plus icon"></i>従業員コード
              </th>
              <th>
                <i class="info circle icon"></i>従業員名
              </th>
              <th>
                <i class="edit icon"></i>
              </th>
            </tr>
          </thead>
          <tr v-for="(employee, i) in employees" :key="i">
            <td>{{ employee.cd }}</td>
            <td>{{ employee.name }}</td>
            <td width="75" class="center aligned" @click.prevent="onDestroy(employee)">
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
    employee: {
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
      employees: [],
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
    onDestroy: function(employee) {
      this.$parent.employee.cd = employee.cd;
      this.$parent.employee.name = employee.name;
      this.$parent.estimate.employeeCd = employee.cd
      this.showContent = false;
    },
    async searchCustomers() {
      this.employees = await api.getEmployeesByName(this.search.name);
      this.employees = this.employees.employees;
      this.showContent = true;
    }
  }
};
</script>