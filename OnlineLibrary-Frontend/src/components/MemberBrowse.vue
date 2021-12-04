<template>
  <body>
    <Header />
    <main>
      <h1>Browse all library items</h1>
      <b-form inline class="search-form" @submit="search">
        <b-form-input
          id="search-bar"
          type="search"
          placeholder="Search"
          list="item-list"
          v-model="searchQuery"
        ></b-form-input>
        <datalist id="item-list">
          <option v-for="item in items" :key="item.id">
            {{ item.title || item.periodicalTitle }}
          </option>
        </datalist>
        <b-button variant="primary" type="submit">Search</b-button>
      </b-form>
      <table>
        <tr>
          <th>Item ID</th>
          <th>Item description</th>
          <th>Item details</th>
        </tr>
        <tr v-for="item in filteredItems" :key="item.id">
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
                <dt>Publication date</dt>
                <dd>{{ item.publicationDate }}</dd>
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
            <router-link
              :to="{ name: 'MemberItem', params: { itemId: item.id } }"
            >
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
import Header from "./MemberHeader.vue";
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "MemberBrowse",
  components: {
    Header
  },
  created: function() {
    axios_instance
      .get("/browse/")
      .then(response => {
        this.items = response.data;
        this.filteredItems = this.items;
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
      filteredItems: [],
      searchQuery: "",
      errorMessage: ""
    };
  },
  methods: {
    search(event) {
      event.preventDefault();
      this.filteredItems = this.items.filter(item => {
        if (item.title) {
          return item.title
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase());
        } else if (item.periodicalTitle) {
          return item.periodicalTitle
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase());
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.search-form {
  margin: 20px 0;
  justify-content: stretch;
  justify-content: space-evenly;
}
.search-form input {
  margin: 0 10px;
  flex-grow: 1;
}
.search-form button {
  flex-grow: 0.1;
}
.error-message {
  margin: 20px;
  text-align: center;
  color: red;
}
.item-description-container {
  display: flex;
}
.item-description-container img {
  max-width: 50%;
  padding: 20px;
}
@media only screen and (max-width: 768px) {
  .item-description-container img {
    display: none;
  }
}
</style>
