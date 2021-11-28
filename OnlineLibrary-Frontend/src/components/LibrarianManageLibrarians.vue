<template>
  <body>
    <Header />
    <main>
      <h1>Manage librarians</h1>
      <p class="error-message" v-if="errorMsg">{{ errorMsg }}</p>
      <table>
        <tr>
          <th>Full name</th>
          <th>Username</th>
          <th></th>
        </tr>
        <tr v-for="librarian in librarians" :key="librarian.id">
          <td>{{ librarian.fullName }}</td>
          <td>{{ librarian.username }}</td>
          <td>
            <b-button v-on:click="deleteLibrarian(librarian.id)"
              >Delete</b-button
            >
          </td>
          <!-- TODO: Let head librarian delete librarians -->
        </tr>
      </table>
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
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "LibrarianManageMembers",
  components: {
    Header,
  },
  created: function () {
    console.log("Created");
    AXIOS.get("/librarian/all")
      .then((response) => {
        this.librarians = response.data.filter(
          (librarian) => !librarian.isHead
        );
        this.errorMsg = "";
      })
      .catch((error) => {
        this.errorMsg =
          "Oops! 🙁 Something bad happened on our side. Try again later.";
      });
  },
  data() {
    return {
      librarians: [],
      errorMsg: "",
    };
  },
  methods: {
    deleteLibrarian(id) {
      const relativeUrl = "/librarian/" + id;
      AXIOS.delete(relativeUrl)
        .then((response) => {
          if (response.status === 200) {
            this.librarians = this.librarians.filter(
              (librarian) => librarian.id !== id
            );
          } else {
            this.error = "Unable to delete librarian. Try again later.";
          }
        })
        .catch((error) => {
          this.error = "Unable to delete librarian. Try again later.";
        });
    },
  },
};
</script>

<style scoped>
.error-message {
  margin: 20px;
  text-align: center;
  color: red;
}
</style>
