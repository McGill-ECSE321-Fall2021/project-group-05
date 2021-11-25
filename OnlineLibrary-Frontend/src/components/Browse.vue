<template>
  <body>
    <Header />
    <main>
      <h1>Browse all library items</h1>
      <table>
        <tr>
          <th>Item ID</th>
          <th>Item description</th>
          <th>Item details</th>
        </tr>
        <tr v-for="item in items" :key="item.id">
          <td>{{ item.id }}</td>
          <td v-if="item.type === 'Book'">
            <div class="item-description-container">
              <img src="../assets/book.svg" alt="Book cover" />
              <dl>
                <dt>Title</dt>
                <dd>{{ item.title }}</dd>
                <dt>Author</dt>
                <dd>{{ item.author }}</dd>
                <dt>Number of pages</dt>
                <dd>{{ item.numberOfPage }}</dd>
                <dt>ISBN</dt>
                <dd>{{ item.isbn }}</dd>
              </dl>
            </div>
          </td>
          <td v-else-if="item.type === 'Album'">
            <div class="item-description-container">
              <img src="../assets/album.svg" alt="Album cover" />
              <dl>
                <dt>Title</dt>
                <dd>{{ item.title }}</dd>
                <dt>Composer/performer</dt>
                <dd>{{ item.composerPerformer }}</dd>
                <dt>Genre</dt>
                <dd>{{ item.genre }}</dd>
              </dl>
            </div>
          </td>
          <td v-else-if="item.type === 'Archive'">
            <div class="item-description-container">
              <img src="../assets/archive.svg" alt="Archive box" />
              <dl>
                <dt>Title</dt>
                <dd>{{ item.title }}</dd>
                <dt>Description</dt>
                <dd>{{ item.description }}</dd>
              </dl>
            </div>
          </td>
          <td v-else-if="item.type === 'Movie'">
            <div class="item-description-container">
              <img src="../assets/movie.svg" alt="Movie icon" />
              <dl>
                <dt>Title</dt>
                <dd>{{ item.title }}</dd>
                <dt>Director</dt>
                <dd>{{ item.director }}</dd>
                <dt>Genre</dt>
                <dd>{{ item.genre }}</dd>
                <dt>Duration</dt>
                <dd>{{ item.length }}</dd>
              </dl>
            </div>
          </td>
          <td v-else-if="item.type === 'Newspaper'">
            <div class="item-description-container">
              <img src="../assets/newspaper.svg" alt="Newspaper" />
              <dl>
                <dt>Title</dt>
                <dd>{{ item.periodicalTitle }}</dd>
                <dt>Date</dt>
                <dd>{{ item.date }}</dd>
                <dt>Frequency</dt>
                <dd>{{ item.frequency }}</dd>
                <dt>Publication number</dt>
                <dd>{{ item.number }}</dd>
              </dl>
            </div>
          </td>
          <td v-else>Could not find item description</td>
          <td>
            <router-link :to="{ name: 'Item', params: { itemId: item.id } }">
              View item details
            </router-link>
          </td>
        </tr>
      </table>
      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </main>
  </body>
</template>

<script>
import Header from "./Header.vue";
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.backendHost}:${config.build.backendPort}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.host}:${config.build.port}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "Browse",
  components: {
    Header
  },
  created: function() {
    axios_instance
      .get("/browse/")
      .then(response => {
        this.items = response.data;
        this.errorMessage = "";
        console.log(response.data);
      })
      .catch(error => {
        console.error(error);
        this.errorMessage =
          "Oops! 🙁 Something bad happened on our side. Try again later";
      });
  },
  data() {
    return {
      items: [],
      errorMessage: "",
    };
  }
};
</script>

<style scoped>
  .error-message {
    margin: 20px;
    text-align: center;
    color: red;
  }
  .item-description-container {
    display: flex;
  }
  .item-description-container img {
    padding: 20px;
  }
</style>
