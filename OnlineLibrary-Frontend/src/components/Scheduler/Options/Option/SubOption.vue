<template>
  <main class="container" @click="emitClick">
    {{ details }}
  </main>
</template>

<script>
export default {
  name: "SubOption",
  props: ['details'],
  methods: {
    emitClick() {
      this.$emit('click');
    }
  }
}
</script>

<style scoped>
  .container {
    margin-top: 1vw;
    border-bottom: 0.2rem solid black;
    background-color: black;
    border-radius: 1rem;
    color: whitesmoke;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: center;
  }
  .container:hover {
    cursor: pointer;
    opacity: 0.7;
  }
</style>
