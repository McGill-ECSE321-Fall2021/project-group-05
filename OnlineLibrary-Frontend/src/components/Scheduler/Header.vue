<template>
  <div class="">
    <div class="container">
      <div class="iconContainer">
        <BIconCalendar2RangeFill @click="store.toggleShowPicker()"/>
      </div>
      <h1>{{ title }}</h1>
    </div>
  </div>
</template>

<script>
import { BIconCalendar2RangeFill } from 'bootstrap-vue';

export default {
  name: "Header",
  props: ['title', 'store'],
  components: {
    BIconCalendar2RangeFill
  }
}
</script>

<style scoped>
  main {
    margin-top: 4vw;
  }
  .container {
    font-size: 1.3rem;
    align-self: center;
    box-sizing: border-box;
    display: inline-flex;
    justify-content: center;
  }
  .iconContainer {
    font-size: 3rem;
    display:flex;
    align-items: center;
    box-sizing: border-box;
    margin-left: -3rem;
    margin-right: 3rem;
  }
  .iconContainer:hover {
    cursor: pointer;
    transform: scale(1.2);
  }
</style>
