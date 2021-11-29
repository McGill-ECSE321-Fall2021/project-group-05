<template>
  <body>
    <Header />
    <div class="form">
      <h1>Update login info</h1>
      <b-form @submit="onSubmit" v-if="show">
        <b-form-group id="input-group-1" label="Your Name:" label-for="input-1">
          <b-form-input
            id="input-1"
            v-model="name"
            placeholder="Enter name"
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
            v-model="username"
            placeholder="Enter new Username"
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
            v-model="password"
            placeholder="Password"
            required
          ></b-form-input>
        </b-form-group>

        <!-- <b-form-group id="input-group-4" v-slot="{ ariaDescribedby }">
          <b-form-checkbox-group
            v-model="form.checked"
            id="checkboxes-4"
            :aria-describedby="ariaDescribedby"
          >
            <b-form-checkbox value="me">Show Password</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group> -->

        <b-button type="submit" variant="primary">Submit</b-button>
      </b-form>
    </div>
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
      show: true,
    };
  },
  created: function () {
    const librarian = JSON.parse(
      sessionStorage.getItem("loggedInLibrarian")
    ).librarian;
    const id = librarian.id;
    const relativeURL = "/librarian/" + id + "/";
    axios_instance
      .get(relativeURL)
      .then((response) => {
        this.librarian = response.data;
        this.id = this.$route.params.id;
        console.log(response.data);
      })
      .catch((error) => console.log(error));
  },

  methods: {
    onSubmit() {
      const librarian = JSON.parse(
        sessionStorage.getItem("loggedInLibrarian")
      ).librarian;
      axios_instance
        .put(
          `/librarian/${librarian.id}`,
          {},
          {
            params: {
              fullName: this.name,
              username: this.username,
              password: this.password,
              id: librarian.id,
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const updateLibrarian = response.data.id;
          this.$router.push({
            name: "librarian",
            params: { librarianId: updateLibrarian },
          });
        })
        .catch((error) => {
          this.errorMessage =
            "could not update your login info, please try again";
        });
    },
  },
};
</script>

<style scoped>
.form {
  margin: 20px;
}
</style>
