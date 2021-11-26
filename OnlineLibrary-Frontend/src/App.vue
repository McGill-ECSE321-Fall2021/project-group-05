<template>
    <div id="app">
      <router-view></router-view>
    </div>
</template>

<script>
export default {
  name: "app"
};
</script>

<style>
@import "./assets/normalize.css";

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

table {
  border-collapse: collapse;
  width: 100%;
}

table td,
table th {
  border: 1px solid #ddd;
  padding: 6px;
}

table tr:nth-child(even) {
  background-color: #f2f2f2;
}

table tr:hover {
  background-color: #ddd;
}

table th {
  color: #fff;
  background-color: #000;
}

h1 {
  text-align: center;
}

main {
  padding: 20px;
  max-width: 1000px;
  margin: auto;
  text-align: left;
}

h2 {
  font-size: 1.5em;
}
</style>
