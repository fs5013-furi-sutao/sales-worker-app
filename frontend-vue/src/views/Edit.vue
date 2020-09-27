<template>
  <div>
    <h1>見積編集</h1>
    <estimate-form @createOrUpdate="createOrUpdate" :task=this.task></estimate-form>
  </div>
</template>

<script>
import EstimateForm from '../components/EstimateForm.vue';
import { api } from '../helpers/Helpers';

export default {
  name: 'edit',
  components: {
    'estimate-form': EstimateForm
  },
  data: function() {
    return {
      task: {}
    };
  },
  methods: {
    createOrUpdate: async function(task) {
      await api.updatetask(task);
      this.flash('task updated sucessfully!', 'success');
      this.$router.push(`/tasks/${task._id}`);
    }
  },
  async mounted() {
    this.task = await api.gettask(this.$route.params.id);
  }
};
</script>