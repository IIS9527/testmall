<template>
  <div id="app">
    <audio controls="controls" id="audio" hidden src="./assets/yisell_sound_2008081315382263271_88012.mp3" ref="audio" preload="metadata"></audio>
    <router-view/>
  </div>
</template>
<script>
import { count } from '@/api/order'
import router from "@/router"; // Secondary package based on el-pagination
export default{
  name: 'App',
  data(){
    return{
      orderCount:0
    }
  },
  mounted() {
    this.getCount()
  },
  methods:{
    getCount() {
    let countNumber= 0
      setInterval(
        ()=>{count().then(res=>{
        let data1 = res.data.data
        if (countNumber === 0) {
          console.log(this.orderCount,res)
          countNumber = res.data.data
        }
        if (countNumber !== data1) {
          console.log(this.$refs.audio)
          this.$refs.audio.play();
          countNumber = res.data.data
          console.log(countNumber,res.data.data,data1)
          this.$notify.success({
            title: '您有新的订单',
            duration:3600*1000,
            onClick() {console.log("点击弹窗");}
          })
        }
      })  }, 10000);
    }
  }
}
</script>
