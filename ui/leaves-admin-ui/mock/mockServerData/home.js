// mock数据模拟
import Mock from 'mockjs'

// 图表数据
let lineList = []
let barList = []
let pieList = [
    {
        name: '小米',
        value: 2999
    },
    {
        name: '苹果',
        value: 5999
    },
    {
        name: 'vivo',
        value: 1500
    },
    {
        name: 'oppo',
        value: 1999
    },
    {
        name: '魅族',
        value: 2200
    },
    {
        name: '三星',
        value: 4500
    }
]
export default {
    getStatisticalData: () => {
        //Mock.Random.float 产生随机数100到8000之间 保留小数 最小0位 最大0位
        for (let i = 0; i < 7; i++) {
            lineList.push(
                Mock.mock({
                    苹果: Mock.Random.float(100, 8000, 0, 0),
                    vivo: Mock.Random.float(100, 8000, 0, 0),
                    oppo: Mock.Random.float(100, 8000, 0, 0),
                    魅族: Mock.Random.float(100, 8000, 0, 0),
                    三星: Mock.Random.float(100, 8000, 0, 0),
                    小米: Mock.Random.float(100, 8000, 0, 0)
                })
            );
            barList.push(
                Mock.mock({
                    新增用户:Mock.Random.float(1,1000,0,0),
                    活跃用户:Mock.Random.float(1,1000,0,0),
                })
            );
        }
        return {
            code: '20000',
            data: {
                // 饼图
                pieData: {
                    date:[],
                    data: pieList
                },
                // 柱状图
                barData:{
                    date:['周一','周二','周三','周四','周五','周六','周日'],
                    data:barList
                },
                // 折线图
                lineData: {
                    date: ['20191001', '20191002', '20191003', '20191004', '20191005', '20191006', '20191007'],
                    data: lineList
                },


                tableData: [
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
                ]
            }
        }
    }
}