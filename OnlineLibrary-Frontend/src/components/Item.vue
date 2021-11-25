<template>
  <body>
    <Header />
    <main v-if="this.item.type === 'Book'">
      <h1>Book</h1>
      <h2>{{ this.item.title }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.item.id }}</dd>
        <dt>Title</dt>
        <dd>{{ this.item.title }}</dd>
        <dt>Author</dt>
        <dd>{{ this.item.author }}</dd>
        <dt>Number of pages</dt>
        <dd>{{ this.item.numberOfPage }}</dd>
        <dt>ISBN</dt>
        <dd>{{ this.item.isbn }}</dd>
      </dl>
    </main>
    <main v-else-if="this.item.type === 'Album'">
      <h1>Album</h1>
      <h2>{{ this.item.title }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.item.id }}</dd>
        <dt>Title</dt>
        <dd>{{ this.item.title }}</dd>
        <dt>Composer/performer</dt>
        <dd>{{ this.item.composerPerformer }}</dd>
        <dt>Genre</dt>
        <dd>{{ this.item.genre }}</dd>
      </dl>
    </main>
    <main v-else-if="this.item.type === 'Archive'">
      <h1>Archive</h1>
      <h2>{{ this.item.title }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.item.id }}</dd>
        <dt>Title</dt>
        <dd>{{ this.item.title }}</dd>
        <dt>Description</dt>
        <dd>{{ this.item.description }}</dd>
      </dl>
    </main>
    <main v-else-if="this.item.type === 'Movie'">
      <h1>Movie</h1>
      <h2>{{ this.item.title }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.item.id }}</dd>
        <dt>Title</dt>
        <dd>{{ this.item.title }}</dd>
        <dt>Director</dt>
        <dd>{{ this.item.director }}</dd>
        <dt>Genre</dt>
        <dd>{{ this.item.genre }}</dd>
        <dt>Duration</dt>
        <dd>{{ this.item.length }}</dd>
      </dl>
    </main>
    <main v-else-if="this.item.type === 'Newspaper'">
      <h1>Newspaper</h1>
      <h2>{{ this.item.periodicalTitle }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.item.id }}</dd>
        <dt>Title of the periodical</dt>
        <dd>{{ this.item.periodicalTitle }}</dd>
        <dt>Date</dt>
        <dd>{{ this.item.date }}</dd>
        <dt>Frequency</dt>
        <dd>{{ this.item.frequency }}</dd>
        <dt>Publication number</dt>
        <dd>{{ this.item.number }}</dd>
      </dl>
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
  name: "Item",
  data() {
    return {
      item: {}
    };
  },
  components: {
    Header
  },
  created() {
    axios_instance
      .get(`/libraryItemInfo/${this.$route.params.itemId}`)
      .then(response => {
        console.log(response.data);
        this.item = response.data;
      })
      .catch(error => {
        console.log(error);
        this.$router.replace({ name: "NotFound" });
      });
  },
  beforeRouteUpdate(to, from, next) {
    console.log("Updating route...");
    axios_instance
      .get(`/libraryItemInfo/${to.params.itemId}`)
      .then(response => {
        this.item = response.data;
        next();
      })
      .catch(error => {
        console.log(error);
        next({ name: "NotFound" });
      });
  }
};
</script>
