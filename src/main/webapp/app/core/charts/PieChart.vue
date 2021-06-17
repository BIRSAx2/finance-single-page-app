<script lang="ts">
import { mixins, Pie } from 'vue-chartjs';

const { reactiveProp } = mixins;

export default {
  extends: Pie,
  props: ['options', 'chart-data'],
  mixins: [reactiveProp],
  mounted() {
    this.renderChart(this.chartData, this.options);
  },
};
</script>
