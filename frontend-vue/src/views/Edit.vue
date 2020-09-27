<template>
  <div>
    <h1>見積編集</h1>
    <estimate-form
      @createOrUpdate="createOrUpdate"
      :estimate="this.estimate"
      :estimateDetails="this.estimateDetails"
      :buttonName="this.buttonName"
      :nextDetailSubId="this.nextDetailSubId"
    ></estimate-form>
  </div>
</template>

<script>
import EstimateForm from "../components/EstimateForm.vue";
import { api } from "../helpers/Helpers";

export default {
  name: "edit",
  components: {
    "estimate-form": EstimateForm
  },
  props: {
    buttonName: {
      type: String,
      required: false,
      default: () => {
        return "見積更新";
      }
    },
    nextDetailSubId: {
      type: Number,
      required: false,
      default: () => {
        return 1;
      }
    },
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
          amount: ""
        };
      }
    },
    estimateDetails: {
      type: Array,
      required: false,
      default: () => {
        return [];
      }
    }
  },
  data: function() {
    return {
      task: {},
      estimate: {},
      //   estimateDetails: [],
      customer: {},
      employee: {}
    };
  },
  methods: {
    // async createOrUpdate(task) {
    //   alert("bigger");
    //   await api.updatetask(task);
    //   this.flash("task updated sucessfully!", "success");
    //   this.$router.push(`/estimate/${task._id}`);
    // },
    async createOrUpdate(estimate) {
      const resEstimate = await api.updateEstimate(estimate);
      console.log("resEstimate=");
      console.log(resEstimate);

    //   for (const [key, value] of estimateDetails.entries()) {
    //     console.log(key);
    //     value["estimateId"] = resEstimate.id;
    //   }
    //   console.log("estimateDetails=");
    //   console.log(estimateDetails);
    //   for (let ed of estimateDetails) {
    //     console.log("ed=");
    //     console.log(ed);
    //     let resEstimateDetails = await api.createEatimateDetail(ed);
    //     console.log("resEstimateDetails=");
    //     console.log(resEstimateDetails);
    //   }
    //   console.log("resEstimate.id=");
    //   console.log(resEstimate.id);
      this.flash("estimate updated", "success");
      this.$router.push(`/estimates/${resEstimate.id}`);
    },
    calcBudgetOver: function() {
      this.estimate.budgetOver =
        this.estimate.amount - this.estimate.budgetedAmount;
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

    let maxSubId = 1;
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

      if (maxSubId < value["subId"]) maxSubId = value["subId"];
    }
    this.nextDetailSubId = ++maxSubId;
  }
};
</script>