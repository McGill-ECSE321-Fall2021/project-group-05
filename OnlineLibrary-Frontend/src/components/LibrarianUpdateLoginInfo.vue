<template>
  <body>
    <Header />
    <h1>Update login info</h1>
    <main>
      <b-form @submit="onSubmit" v-if="show">
        <b-form-group id="input-group-1" label="Your Name:" label-for="input-1">
          <b-form-input
            id="input-1"
            v-model="form.name"
            :placeholder="name"
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-2"
          label="Your new Username:"
          label-for="input-2"
        >
          <b-form-input
            id="input-2"
            v-model="form.username"
            :placeholder="username"
            @keydown.space.prevent
            required
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="input-group-3"
          label="Your new Password:"
          label-for="input-3"
        >
          <b-form-input
            id="input-3"
            type="password"
            v-model="form.password"
            placeholder="Password"
            required
            @keydown.space.prevent
            :state="passwordState()"
            aria-describedby="input-live-feedback"
          ></b-form-input>
          <b-form-input
            id="input-4"
            type="password"
            v-model="form.confirmPassword"
            placeholder="Confirm password"
            required
            @keydown.space.prevent
            :state="passwordState()"
            aria-describedby="input-live-feedback"
          ></b-form-input>
          <b-form-invalid-feedback id="input-live-feedback">
            {{ this.confirmPassword }}
          </b-form-invalid-feedback>
        </b-form-group>
        <b-button @click="onSubmit" variant="primary">Submit</b-button>
      </b-form>
      <p v-if="errorMessage" class="error-message">
        ERROR: {{ this.errorMessage }}
      </p>
      <p v-if="confirmationMsg" class="confirmation-message">
        {{ this.confirmationMsg }}
      </p>
    </main>
  </body>
</template>

<script>
import Header from "../components/LibrarianHeader.vue";
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
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "LibrarianUpdateLoginInfo",
  components: {
    Header,
  },
  data() {
    return {
      form: {
        name: "",
        username: "",
        password: "",
      },
      confirmPassword: "",
      name: "",
      username: "",
      show: true,
      errorMessage: "",
      confirmationMsg: "",
    };
  },
  created: function () {
    const librarian = JSON.parse(
      sessionStorage.getItem("loggedInLibrarian")
    ).librarian;
    const id = librarian.id;
    const relativeURL = "/librarian/" + id + "/";
    const self = this;
    axios_instance
      .get(relativeURL)
      .then((response) => {
        self.librarian = response.data;
        self.name = response.data.fullName;
        self.username = response.data.username;
        self.form.name = response.data.fullName;
        self.form.username = response.data.username;
      })
      .catch((error) => {
        self.errorMessage =
          "There seems to be an error, please log out and log back in";
        console.log(error);
      });
  },

  methods: {
    passwordState() {
      let size = this.form.password.length > 7;
      let match = this.form.password === this.form.confirmPassword;
      if (!match) {
        this.confirmPassword = "Your passwords do not match";
        return false;
      } else if (!size) {
        this.confirmPassword = "Enter at least 8 characters";
        return false;
      } else return true;
    },
    onSubmit() {
      const librarian = JSON.parse(
        sessionStorage.getItem("loggedInLibrarian")
      ).librarian;
      const self = this;
      axios_instance
        .put(
          `/librarian/${librarian.id}`,
          {},
          {
            params: {
              fullName: self.form.name,
              username: self.form.username,
              password: self.form.password,
              id: librarian.id,
            },
          }
        )
        .then((response) => {
          self.errorMessage = "";
          self.confirmationMsg = "Your info has been updated 😊";
        })
        .catch((error) => {
          this.errorMessage =
            "Could not update your login info, please try again with a different username";
          this.confirmationMsg = "";
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.confirmation-message {
  color: green;
}
.error-message {
  color: red;
}
</style>
