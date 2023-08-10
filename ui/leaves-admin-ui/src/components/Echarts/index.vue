<template>
  <div ref="echart"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  props: {
    //标识是否折线图或者柱状图  饼状图没有xy轴
    isAxisChart: {
      type: Boolean,
      default: true,
    },
    //option数据来源
    chartData: {
      type: Object,
      default() {
        return {
          //x轴数据
          xData: [],
          series: [],
        };
      },
    },
  },
  //监听数据变化
  watch: {
    chartData: {
      handler: function () {
        this.initChart(); //数据改变重新绘制
      },
      deep: true, //首次监听触发重新绘制
    },
  },
  methods: {
    initChart() {
      this.initChartData();
      if (this.echart) {
        this.echart.setOption(this.options);
      } else {
        //DOM节点渲染Echarts
        this.echart = echarts.init(this.$refs.echart);
        this.echart.setOption(this.options);
      }
    },
    //options数据初始化
    initChartData() {
      if (this.isAxisChart) {
        this.axisOption.xAxis.data = this.chartData.xData;
        this.axisOption.series = this.chartData.series;
      } else {
        this.pieOption.series = this.chartData.series;
      }
    },
  },
  data() {
    return {
      axisOption: {
        legend: {
          // 图例文字颜色
          textStyle: {
            color: "#333",
          },
        },
        grid: {
          left: "20%",
        },
        // 提示框
        tooltip: {
          trigger: "axis",
        },
        xAxis: {
          type: "category", // 类目轴
          data: [],
          axisLine: {
            lineStyle: {
              color: "#17b3a3",
            },
          },
          axisLabel: {
            interval: 0,
            color: "#333",
          },
        },
        yAxis: [
          {
            type: "value",
            axisLine: {
              lineStyle: {
                color: "#17b3a3",
              },
            },
          },
        ],
        color: ["#2ec7c9", "#b6a2de"],
        series: [],
      },
      pieOption: {
        tooltip: {
          trigger: "item",
        },
        color: [
          "#0f78f4",
          "#dd536b",
          "#9462e5",
          "#a6a6a6",
          "#e1bb22",
          "#39c362",
          "#3ed1cf",
        ],
        series: [],
      },
      // 用于表示图表是否已经渲染
      echart: null,
    };
  },
  computed: {
    options() {
      return this.isAxisChart ? this.axisOption : this.pieOption;
    },
  },
};
</script>