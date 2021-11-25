<template>
  <body>
    <Header />
    <h1>Welcome to the online library system!</h1>
    <main>
      <div class="item-picture-container">
        <img class="blue" src="../assets/book.svg" alt="Book cover" />
        <img class="red" src="../assets/album.svg" alt="Album cover" />
        <img class="yellow" src="../assets/archive.svg" alt="Archive box" />
        <img class="green" src="../assets/movie.svg" alt="Movie icon" />
        <img class="purple" src="../assets/newspaper.svg" alt="Newspaper" />
      </div>
    </main>
  </body>
</template>

<script>
import Header from "./LibrarianHeader.vue";
export default {
  components: { Header },
  name: "LibrarianHome"
};
</script>

<style scoped>
h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}
.item-picture-container {
  display: flex;
  justify-content: space-evenly;
}
</style>
