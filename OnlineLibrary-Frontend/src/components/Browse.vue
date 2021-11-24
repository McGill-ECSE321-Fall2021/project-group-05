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
            <h3>Book</h3>
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
          </td>
          <td v-else-if="item.type === 'Album'">
            <h3>Album</h3>
            <dl>
              <dt>Title</dt>
              <dd>{{ item.title }}</dd>
              <dt>Composer/performer</dt>
              <dd>{{ item.composerPerformer }}</dd>
              <dt>Genre</dt>
              <dd>{{ item.genre }}</dd>
            </dl>
          </td>
          <td v-else-if="item.type === 'Archive'">
            <h3>Archive</h3>
            <dl>
              <dt>Title</dt>
              <dd>{{ item.title }}</dd>
              <dt>Description</dt>
              <dd>{{ item.description }}</dd>
            </dl>
          </td>
          <td v-else-if="item.type === 'Movie'">
            <h3>Movie</h3>
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
          </td>
          <td v-else-if="item.type === 'Newspaper'">
            <h3>Newspaper</h3>
            <dl>
              <dt>Date</dt>
              <dd>{{ item.date }}</dd>
              <dt>Frequency</dt>
              <dd>{{ item.frequency }}</dd>
              <dt>Publication number</dt>
              <dd>{{ item.number }}</dd>
            </dl>
          </td>
          <td v-else>Could not find item description</td>
          <td>
            <router-link :to="{ name: 'Item', params: { itemId: item.id } }">
              View item details
            </router-link>
          </td>
        </tr>
      </table>
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
        console.log(response.data);
      })
      .catch(error => console.log(error));
  },
  data() {
    return {
      items: []
    };
  }
};
</script>
