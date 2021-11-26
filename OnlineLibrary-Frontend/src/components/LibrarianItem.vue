<template>
  <body>
    <Header />
    <main v-if="this.item.type === 'Book'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
        <div class="item-description-container">
          <img src="../assets/book.svg" alt="Book cover" />
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
        </div>
        <div class="item-actions-container">
          <h2>Update item details</h2>
          <b-form @submit="updateBook">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Author" label-for="author">
              <b-form-input
                id="author"
                type="text"
                v-model="newItem.author"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Number of pages" label-for="number-of-pages">
              <b-form-input
                id="number-of-pages"
                type="number"
                v-model="newItem.numberOfPage"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="ISBN" label-for="isbn">
              <b-form-input
                id="isbn"
                type="number"
                v-model="newItem.isbn"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
        </div>
      </div>
    </main>
    <main v-else-if="this.item.type === 'Album'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
        <div class="item-description-container">
          <img src="../assets/album.svg" alt="Album cover" />
          <dl>
            <dt>Id</dt>
            <dd>{{ this.item.id }}</dd>
            <dt>Title</dt>
            <dd>{{ this.item.title }}</dd>
            <dt>Composer / Performer</dt>
            <dd>{{ this.item.composerPerformer }}</dd>
            <dt>Genre</dt>
            <dd>{{ this.item.genre }}</dd>
          </dl>
        </div>
        <div class="item-actions-container">
          <h2>Update item details</h2>
          <b-form @submit="updateAlbum">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group
              label="Composer / Performer"
              label-for="composer-performer"
            >
              <b-form-input
                id="composer-performer"
                type="text"
                v-model="newItem.composerPerformer"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Genre" label-for="genre">
              <b-form-input
                id="genre"
                type="text"
                v-model="newItem.genre"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
        </div>
      </div>
    </main>
    <main v-else-if="this.item.type === 'Archive'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="item-description-container">
        <img src="../assets/archive.svg" alt="Archive box" />
        <dl>
          <dt>Id</dt>
          <dd>{{ this.item.id }}</dd>
          <dt>Title</dt>
          <dd>{{ this.item.title }}</dd>
          <dt>Description</dt>
          <dd>{{ this.item.description }}</dd>
        </dl>
      </div>
    </main>
    <main v-else-if="this.item.type === 'Movie'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="item-description-container">
        <img src="../assets/movie.svg" alt="Movie icon" />
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
      </div>
    </main>
    <main v-else-if="this.item.type === 'Newspaper'">
      <h1>{{ this.item.periodicalTitle }}</h1>
      <hr />
      <div class="item-description-container">
        <img src="../assets/newspaper.svg" alt="Newspaper" />
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
      </div>
    </main>
  </body>
</template>

<script>
import Header from "./LibrarianHeader.vue";
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "LibrarianItem",
  data() {
    return {
      item: {},
      newItem: {},
      updateItemSuccessMessage: "",
      updateItemErrorMessage: ""
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
        this.newItem = { ...this.item };
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
        this.newItem = { ...this.item };
        next();
      })
      .catch(error => {
        console.log(error);
        next({ name: "NotFound" });
      });
  },
  methods: {
    updateItem(event, endpoint) {
      event.preventDefault();
      axios_instance
        .put(
          `/${endpoint}/${this.item.id}`,
          {},
          { params: { ...this.newItem } }
        )
        .then(response => {
          this.updateItemSuccessMessage = "Item details updated successfully";
          this.updateItemErrorMessage = "";
          this.item = response.data;
        })
        .catch(error => {
          this.updateItemSuccessMessage = "";
          this.updateItemErrorMessage = "Could not update item details";
          console.error(error);
        });
    },
    updateBook(event) {
      this.updateItem(event, "bookInfo");
    },
    updateAlbum(event) {
      this.updateItem(event, "albumInfo");
    },
    updateArchive(event) {
      this.updateItem(event, "archiveInfo");
    },
    updateMovie(event) {
      this.updateItem(event, "movieInfo");
    },
    updateNewspaper(event) {
      this.updateItem(event, "newspaperInfo");
    }
  }
};
</script>

<style scoped>
.item-description-container {
  display: flex;
}
.item-description-container img {
  padding: 20px;
  width: 200px;
}
</style>
