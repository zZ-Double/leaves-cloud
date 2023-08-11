<template>

<div>欢迎</div>

</template>

<script setup lang="ts">

</script>
<style scoped lang="scss">

</style>
<!-- <template>
  <el-row class="home" :gutter="20">
    <el-col :span="8" style="margin-top: 20px">
      <el-card shadow="hover">
        <div class="user">
          <img src="https://himg.bdimg.com/sys/portraitn/item/public.1.8fc9ed39.xkow_8A-T_VOQZ5iSMYg9Q"/>
          <div class="userinfo">
            <p class="name">扬一</p>
            <p class="access">超级管理员</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登录时间：<span>2021-07-19</span></p>
          <p>上次登录地点：<span>北京</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px; height: 460px">
        <el-table :data="tableData">
          <el-table-column
            v-for="(val, key) in tableLabel"
            :key="key"
            :label="val"
            :prop="key"
          ></el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="16" style="margin-top: 20px">
      <div class="order">
        <el-card
          v-for="item in countData"
          :key="item.name"
          :body-style="{ display: 'flex', padding: 0 }"
        >
          <i
            class="icon"
            :class="`el-icon-${item.icon}`"
            :style="{ background: item.color }"
          ></i>
          <div class="detail">
            <p class="num">¥{{ item.value }}</p>
            <p class="txt">{{ item.name }}</p>
          </div>
        </el-card>
      </div>
      <el-card style="height: 280px">
        <Echarts :chart-data="echartData.line" style="height: 280px" />
      </el-card>
      <div class="graph">
        <el-card style="height: 260px">
          <Echarts :chart-data="echartData.bar" style="height: 240px" />
        </el-card>
        <el-card style="height: 260px">
          <Echarts :chart-data="echartData.pie" :is-axis-chart="false" style="height: 240px" />
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { getData } from "../../../mock/data.js";
import Echarts from "@/components/Echarts";

export default {
  name: "Dashboard",
  components: {
    Echarts,
  },
  data() {
    return {
      tableLabel: {
        name: "品牌",
        todayBuy: "今日购买",
        monthBuy: "本月购买",
        totalBuy: "总购买",
      },
      tableData: [],
      countData: [
        {
          name: "今日支付订单",
          value: 1234,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "今日收藏订单",
          value: 1234,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          name: "今日未支付订单",
          value: 1234,
          icon: "s-goods",
          color: "#5ab1ef",
        },
        {
          name: "本月支付订单",
          value: 1234,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "本月收藏订单",
          value: 1234,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          name: "本月未支付订单",
          value: 1234,
          icon: "s-goods",
          color: "#5ab1ef",
        },
      ],
      echartData: {
        line: {
          xData: [],
          series: [],
        },
        bar: {
          xData: [],
          series: [],
        },
        pie: {
          series: [],
        },
      },
    };
  },
  mounted() {
    getData().then((res) => {
      const { code, data } = res.data;
      if (code === "20000") {
        //表格数据
        this.tableData = data.tableData;

        //折线图数据
        const lineSeries = [];
        Object.keys(data.lineData.data[0]).forEach((key) => {
          lineSeries.push({
            name: key,
            data: data.lineData.data.map((item) => item[key]),
            type: "line",
          });
        });
        this.echartData.line.xData = data.lineData.date;
        this.echartData.line.series = lineSeries;

        //柱状图数据
        const barSeries = [];
        Object.keys(data.barData.data[0]).forEach((key) => {
          barSeries.push({
            name: key,
            data: data.barData.data.map((item) => item[key]),
            type: "bar",
          });
        });
        this.echartData.bar.xData = data.barData.date;
        this.echartData.bar.series = barSeries;

        //饼状图数据
        const pieSeries = [];

        pieSeries.push({
          data: data.pieData.data,
          type: "pie",
        });

        this.echartData.pie.series = pieSeries;
      }
    });
  },
};
</script>

<style lang="scss" scoped>
.home {
  .user {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #ccc;
    img {
      width: 150px;
      height: 150px;
      border-radius: 50%;
      margin-right: 40px;
    }
    &info {
      .name {
        font-size: 32px;
        margin-bottom: 10px;
      }
      .access {
        color: #999999;
      }
    }
  }
  .login-info {
    p {
      line-height: 28px;
      font-size: 14px;
      color: #999999;
      span {
        color: #666666;
        margin-left: 60px;
      }
    }
  }
  .order {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .el-card {
      width: 32%;
      margin-bottom: 20px;
    }
    .icon {
      font-size: 30px;
      width: 80px;
      height: 80px;
      text-align: center;
      line-height: 80px;
      color: #fff;
    }
    .detail {
      margin-left: 15px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      .num {
        font-size: 30px;
        margin-bottom: 10px;
      }
      .txt {
        font-size: 14px;
        text-align: center;
        color: #999999;
      }
    }
  }
  .graph {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    .el-card {
      width: 48%;
    }
  }
}
</style> -->