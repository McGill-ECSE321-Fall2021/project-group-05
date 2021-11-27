<template>
  <div class="form-container">
    <h2>Add a newspaper</h2>
    <b-form @submit="createNewspaper">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="newspaper-title"
          type="text"
          placeholder="Title"
          v-model="newspaperTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Publication Date"
        label-for="input-2"
      >
        <b-form-input
          id="newspaper-date"
          type="date"
          
          v-model="newspaperDate"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Frequency" label-for="input-3">
        <b-form-input
          id="newspaper-frequency"
          type="text"
          placeholder="Frequency"
          v-model="newspaperFrequency"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="Number" label-for="input-4">
        <b-form-input
          id="newspaper-number"
          type="number"
          placeholder="Number"
          v-model="newspaperNumber"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add newspaper</b-button>

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
  name: "AddNewspaper",
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