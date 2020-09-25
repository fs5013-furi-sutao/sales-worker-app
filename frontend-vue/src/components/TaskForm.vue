<template>
  <form action="#" @submit.prevent="onSubmit">
    <p v-if="errorsPresent" class="error">Please fill out both fields!</p>

    <div class="ui stackable two column grid">
      <div class="column">
        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積案件名
          </div>
          <input type="text" placeholder="Enter task..." v-model="task.task1" />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 顧客名</div>
          <input
            type="text"
            placeholder="Enter Details"
            v-model="task.task2"
          /><button class="primary ui button mleft fixsizebutton">
            顧客検索
          </button>
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 担当者名</div>
          <input
            type="text"
            placeholder="Enter Details"
            v-model="task.task2"
          /><button class="primary ui button mleft fixsizebutton">
            担当者検索
          </button>
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積ステータス
          </div>
          <input type="text" placeholder="Enter task..." v-model="task.task1" />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 予算金額</div>
          <input type="text" placeholder="Enter Details" v-model="task.task2" />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="info circle icon"></i> 予算超過額
          </div>
          <input type="text" placeholder="Enter Details" v-model="task.task2" />
        </div>

        <div class="ui horizontal divider">見積詳細</div>

        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="info circle icon"></i> 商品コード
          </div>
          <input
            class="readonly"
            type="text"
            placeholder="Enter Details"
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

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 商品名</div>
          <input
            class="readonly"
            type="text"
            placeholder="Enter Details"
            v-model="product.name"
            readonly
          />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 価格</div>
          <input
            class="readonly"
            type="text"
            placeholder="Enter Details"
            v-model="product.price"
            readonly
          />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="info circle icon"></i> 数量</div>
          <input type="text" placeholder="Enter Details" v-model="task.task2" />
        </div>

        <div style="float: right;">
          <div class="column">
          <button class="positive ui button mleft fixsizebutton">
            見積品に追加
          </button>
          </div>
        </div>
      </div>

      <div class="six wide column">
        <div class="ui horizontal divider">見積品</div>

        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積ステータス
          </div>
          <input type="text" placeholder="Enter task..." v-model="task.task1" />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label">
            <i class="calendar plus icon"></i>見積ステータス
          </div>
          <input type="text" placeholder="Enter task..." v-model="task.task1" />
        </div>

        <div class="ui labeled input fluid">
          <div class="ui label"><i class="calendar plus icon"></i>合計金額</div>
          <input type="text" placeholder="Enter task..." v-model="task.task1" />
        </div>
      </div>
    </div>
    <!-- class="ui grid" -->

    <button class="huge orange ui button">見積登録</button>
    <ProductSearchModal ref="modal" />
  </form>
</template>

<script>
import ProductSearchModal from "@/components/ProductSearchModal.vue";
export default {
  components: {
    ProductSearchModal,
  },
  name: "task-form",
  props: {
    task: {
      type: Object,
      required: false,
      default: () => {
        return {
          task1: "",
          task2: "",
        };
      },
    },
    product: {
      type: Object,
      required: false,
      default: () => {
        return {
          cd: "",
          name: "",
          price: "",
        };
      },
    },
  },
  data() {
    return {
      errorsPresent: false,
    };
  },
  methods: {
    onSubmit: function () {
      if (this.task.task1 === "" || this.task.task2 === "") {
        this.errorsPresent = true;
      } else {
        this.$emit("createOrUpdate", this.task);
      }
    },
    searchProducts: function () {
      alert("aa");
    },
    getChild(name) {
      for (let child of this.$children)
        if (child.$options.name == name) return child;
    },
    openModal: function () {
      this.$refs.modal.openModal();
    },
  },
};
</script>

<style scoped>
.error {
  color: red;
}
</style>