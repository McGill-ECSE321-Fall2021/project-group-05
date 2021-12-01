<template>
  <div class="main">
    <Option v-for="opt in store.options" v-bind:key="opt.res" :details="opt" :store="store"/>
  </div>
</template>

<script>
import Option from "./Option/Option";

export default {
  name: "OptionsBar",
  props: ['store'],
  components: {Option},
}
</script>

<style scoped>
  .main {
    box-sizing: border-box;
    display: inline-flex;
    margin-left: -2rem;
    margin-bottom: 1.5vw;
    margin-top:0px;
  }
</style>
