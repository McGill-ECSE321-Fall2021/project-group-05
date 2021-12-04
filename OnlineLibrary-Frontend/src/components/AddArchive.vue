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
  name: "AddArchive",
  data() {
    return {
      archiveDate: null,
      archiveDescription: "",
      archiveTitle: "",
      errorMessage: "",
    };
  },
  methods: {
    createArchive(event) {
      event.preventDefault();
      console.log(
        this.archiveDate,
        this.archiveDescription,
        this.archiveTitle
      );
      axios_instance
        .post(
          `/archiveInfo/${this.archiveTitle}`,
          {},
          {
            params: {
              description: this.archiveDescription,
              publicationDate: this.archiveDate,
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const newArchiveId = response.data.id;
          this.$router.push({
            name: "LibrarianItem",
            params: { itemId: newArchiveId },
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