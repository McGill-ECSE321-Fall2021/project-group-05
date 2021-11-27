<template>
  <div class="form-container">
    <h2>Add an archive</h2>
    <b-form @submit="createArchive">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="archive-title"
          type="text"
          placeholder="Title"
          v-model="archiveTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Description"
        label-for="input-2"
      >
        <b-form-input
          id="archive-description"
          type="text"
          placeholder="Description"
          v-model="archiveDescription"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Date" label-for="input-3">
        <b-form-input
          id="archive-date"
          type="date"

          v-model="archiveDate"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add archive</b-button>

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
  name: "AddArchive",
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