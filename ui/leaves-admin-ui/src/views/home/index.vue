<template>
  <el-card>
    <div class="box">
      <img :src="user.avatar" alt="" class="avatar">
      <div class="bottom">
        <h3 class="title">你好呀{{ user.nickname }}</h3>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
//引入用户相关的仓库,获取当前用户的头像、昵称
import useStore from '@/store';
//获取存储用户信息的仓库对象
const { user } = useStore()

</script>

<style scoped lang="scss">
.box {
  display: flex;

  .avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }

  .bottom {
    margin-left: 20px;

    .title {
      font-size: 30px;
      font-weight: 900;
      margin-bottom: 30px;
    }

    .subtitle {
      font-style: italic;
      color: skyblue;
    }
  }
}

.bottoms {
  margin-top: 10px;
  display: flex;
  justify-content: center;
}
</style>