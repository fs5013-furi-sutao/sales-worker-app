<template>
  <div>
    <h1>見積登録</h1>
    <estimate-form @createOrUpdate="createOrUpdate" :buttonName="this.buttonName"></estimate-form>
  </div>
</template>

<script>
import EstimateForm from "../components/EstimateForm.vue";
import { api } from "../helpers/Helpers";
export default {
  name: "new-task",
  components: {
    "estimate-form": EstimateForm
  },
  props: {
    buttonName: {
      type: String,
      required: false,
      default: () => {
        return "見積登録";
      }
    }
  },
  //   data() {
  //     return {
  //       buttonName: "見積編集",
  //     };
  //   },
  methods: {
    createOrUpdate: async function(estimate, estimateDetails) {
      estimate.id = null;
      const resEstimate = await api.createEatimate(estimate);
      for (const [key, value] of estimateDetails.entries()) {
        console.log(key);
        value["estimateId"] = resEstimate.id;
      }
      console.log("estimateDetails=");
      console.log(estimateDetails);
      for (let ed of estimateDetails) {
        console.log("ed=");
        console.log(ed);
        let resEstimateDetails = await api.createEatimateDetail(ed);
        console.log("resEstimateDetails=");
        console.log(resEstimateDetails);
      }
      console.log("resEstimate.id=");
      console.log(resEstimate.id);
      this.flash("estimate created", "success");
      this.$router.push(`/estimates/${resEstimate.id}`);
      //   this.flash("task created", "success");
      //   this.$router.push(`/tasks/${res._id}`);
    }
    // createOrUpdate: async function(task) {
    //   const res = await api.createtask(task);
    //   this.flash("task created", "success");
    //   this.$router.push(`/tasks/${res._id}`);
    // }
  },

  async mounted() {
    this.estimate = {
      id: "",
      name: "",
      status: "",
      customerCd: "",
      employeeCd: "",
      budgetedAmount: "",
      budgetOver: "",
      amount: ""
    };
  }
};
</script>