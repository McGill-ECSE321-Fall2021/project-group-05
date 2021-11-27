<template>
  <div class="form-container">
    <h2>Add an album</h2>
    <b-form @submit="createAlbum">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="album-title"
          type="text"
          placeholder="Title"
          v-model="albumTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Composer or Performer"
        label-for="input-2"
      >
        <b-form-input
          id="album-performer"
          type="text"
          placeholder="Composer/Performer"
          v-model="albumPerformer"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Genre" label-for="input-3">
        <b-form-input
          id="album-genre"
          type="text"
          placeholder="Genre"
          v-model="albumGenre"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add album</b-button>

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

//NOT DONE YET!!!!!!!!!!!!!!!!!!!!!!!!
export default {
  name: "AddAlbum",
  data() {
    return {
      errorMessage: "",
    };
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