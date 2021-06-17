<script lang="ts">
import { Line, mixins } from 'vue-chartjs';

const { reactiveProp } = mixins;

export default {
  extends: Line,
  props: ['options', 'chart-data'],
  mixins: [reactiveProp],
  mounted() {
    this.renderChart(this.chartData, this.options);
  },
};
</script>
