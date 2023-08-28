<template>
  <div class="app-container">
    <el-row class="home" :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="user">
            <img :src="imgUrl" />
            <div class="userinfo">
              <p class="name">{{ user.nickname }}</p>
            </div>
          </div>
          <div class="login-info">
            <p>上次登录时间：<span>2021-07-19</span></p>
            <p>上次登录地点：<span>北京</span></p>
          </div>
        </el-card>
        <el-card style="margin-top: 20px; height: 300px">
          <el-table :data="tableData">
            <el-table-column v-for="(val, key) in tableLabel" :key="key" :label="val" :prop="key"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="16">
        <div class="order">
          <el-card v-for="item in countData" :key="item.name" :body-style="{ display: 'flex', padding: 0 }">
            <i class="icon" :class="`el-icon-${item.icon}`" :style="{ background: item.color }"></i>
            <div class="detail">
              <p class="num">¥{{ item.value }}</p>
              <p class="txt">{{ item.name }}</p>
            </div>
          </el-card>
        </div>
        <!-- <el-card style="height: 280px">
          <Echarts :chart-data="echartData.line" style="height: 280px" />
        </el-card>
        <div class="graph">
          <el-card style="height: 260px">
            <Echarts :chart-data="echartData.bar" style="height: 240px" />
          </el-card>
          <el-card style="height: 260px">
            <Echarts :chart-data="echartData.pie" :is-axis-chart="false" style="height: 240px" />
          </el-card>
        </div> -->
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import useStore from '@/store'

const { user } = useStore()

const imgUrl = user.avatar;
let tableData = ref([
  {
    name: 'oppo',
    todayBuy: 500,
    monthBuy: 3500,
    totalBuy: 22000
  },
  {
    name: 'vivo',
    todayBuy: 300,
    monthBuy: 2200,
    totalBuy: 24000
  },
  {
    name: '苹果',
    todayBuy: 800,
    monthBuy: 4500,
    totalBuy: 65000
  },
  {
    name: '小米',
    todayBuy: 1200,
    monthBuy: 6500,
    totalBuy: 45000
  },
  {
    name: '三星',
    todayBuy: 300,
    monthBuy: 2000,
    totalBuy: 34000
  },
  {
    name: '魅族',
    todayBuy: 350,
    monthBuy: 3000,
    totalBuy: 22000
  }
])
let tableLabel = ref({
  name: "品牌",
  todayBuy: "今日购买",
  monthBuy: "本月购买",
  totalBuy: "总购买",
})

let countData = ref([
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
])

</script>

<style lang="scss" scoped>
.home {
  margin-left: 10px;

  .user {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #ccc;

    img {
      width: 120px;
      height: 120px;
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
      height: 130px;
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
</style>