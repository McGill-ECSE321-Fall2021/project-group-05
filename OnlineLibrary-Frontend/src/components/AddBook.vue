<template>
  <div class="form-container">
    <h2>Add a book</h2>
    <b-form @submit="createBook">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="book-title"
          type="text"
          placeholder="Title"
          v-model="bookTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Number of pages"
        label-for="input-2"
      >
        <b-form-input
          id="book-number-of-pages"
          type="number"
          placeholder="Number of pages"
          v-model="bookNumberOfPages"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Author" label-for="input-3">
        <b-form-input
          id="book-author"
          type="text"
          placeholder="Author"
          v-model="bookAuthor"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="ISBN" label-for="input-4">
        <b-form-input
          id="book-isbn"
          type="number"
          placeholder="ISBN"
          v-model="bookIsbn"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add book</b-button>

      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </b-form>
  </div>
</template>

<script>
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
// Same for the frontend URL which may be used in some API responses...
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "AddBook",
  data() {
    return {
      bookNumberOfPages: NaN,
      bookAuthor: "",
      bookIsbn: NaN,
      bookTitle: "",
      errorMessage: "",
    };
  },
  methods: {
    createBook(event) {
      event.preventDefault();
      console.log(
        this.bookNumberOfPages,
        this.bookAuthor,
        this.bookIsbn,
        this.bookTitle
      );
      axios_instance
        .post(
          `/bookInfo/${this.bookTitle}`,
          {},
          {
            params: {
              numberOfPage: Number.parseInt(this.bookNumberOfPages),
              author: this.bookAuthor,
              isbn: Number.parseInt(this.bookIsbn),
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const newBookId = response.data.id;
          this.$router.push({
            name: "LibrarianItem",
            params: { itemId: newBookId },
          });
        })
        .catch((error) => {
          console.error(error);
          this.errorMessage =
            "Could not create this item";
        });
    },
  },
};
</script>

<style scoped>
.error-message {
  color: red;
}
.form-container {
  margin: 20px;
}
</style>