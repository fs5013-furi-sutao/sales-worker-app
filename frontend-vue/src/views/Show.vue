<template>
  <div>
    <h1>見積詳細</h1>

    <form action="#" @submit.prevent>
      <div class="ui stackable two column grid">
        <div class="ui column"></div>
        <div class="ui column">
          <table class="ui definition table">
            <tr>
              <th>
                <i class="calendar plus icon"></i>見積番号
              </th>
              <td>{{ estimate.id }}</td>
            </tr>
          </table>
        </div>
      </div>
      <!-- <div class="ui stackable two column grid">
        <div class="ui column"></div>
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積番号
            </div>
            <input class="readonly" type="text" placeholder="自動採番" v-model="estimate.id" readonly />
          </div>
        </div>
      </div>-->

      <div class="ui stackable two column grid">
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積案件名
            </div>
            <input type="text" placeholder="例）新規オフィス（伏見）への移転に伴う機器・用品調達" v-model="estimate.name" />
          </div>
        </div>
        <div class="ui column">
          <div class="ui labeled input fluid">
            <div class="ui label">
              <i class="calendar plus icon"></i>見積ステータス
            </div>
            <select
              class="ui dropdown fluid"
              :style="{color: selectedColor}"
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
              >{{ status.name }}</option>
            </select>
          </div>
        </div>
      </div>
    </form>
    <!-- <div class="ui labeled input fluid">
      <div class="ui label">
      <i class="tasks icon"></i>  Task
      </div>
      <input type="text" readonly  :value="task.task1"/>
    </div>

     <div class="ui labeled input fluid">
      <div class="ui label">
        <i class="info circle icon"></i> Details
      </div>
      <input type="text" readonly  :value="task.task2"/>
    </div>-->
    <div class="actions">
      <router-link :to="{ name: 'edit', params: { id: this.$route.params.id }}">Edit task</router-link>
    </div>
  </div>
</template>

<script>
import { api } from "../helpers/Helpers";
export default {
  name: "show",
  props: {
    estimate: {
      type: Object,
      required: false,
      default: () => {
        return {
          id: "1",
          name: "",
          status: "",
          customerCd: "",
          employeeCd: "",
          budgetedAmount: "",
          budgetOver: "",
          amount: ""
        };
      }
    }
  },
  //   created : function(){
  //       alert('aa')
  //       this.estimate = await api.getEstimate(this.$route.params.id);
  //   },
  data() {
    return {
      task: ""
    };
  },
  async mounted() {
    alert("aa");
    this.estimate = await api.getEstimate(this.$route.params.id);
    //this.task = await api.gettask(this.$route.params.id);
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