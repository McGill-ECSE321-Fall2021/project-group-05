<template>
  <div class="form-container">
    <h2>Add a movie</h2>
    <b-form @submit="createMovie">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="movie-title"
          type="text"
          placeholder="Title"
          v-model="movieTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Genre"
        label-for="input-2"
      >
        <b-form-input
          id="movie-genre"
          type="text"
          placeholder="Genre"
          v-model="movieGenre"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Director" label-for="input-3">
        <b-form-input
          id="movie-director"
          type="text"
          placeholder="Director"
          v-model="movieDirector"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="Length" label-for="input-4">
        <b-form-input
          id="movie-length"
          type="number"
          placeholder="Length"
          v-model="movieLength"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add movie</b-button>

      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </b-form>
  </div>
</template>

<script>
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
// Same for the frontend URL which may be used in some API responses...
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "AddMovie",
  data() {
    return {
      movieGenre: "",
      movieDirector: "",
      movieLength: NaN,
      movieTitle: "",
      errorMessage: "",
    };
  },
  methods: {
    createMovie(event) {
      event.preventDefault();
      axios_instance
        .post(
          `/movieInfo/${this.movieTitle}`,
          {},
          {
            params: {
              genre: this.movieGenre,
              director: this.movieDirector,
              length: Number.parseInt(this.movieLength),
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const newMovieId = response.data.id;
          this.$router.push({
            name: "LibrarianItem",
            params: { itemId: newMovieId },
          });
        })
        .catch((error) => {
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