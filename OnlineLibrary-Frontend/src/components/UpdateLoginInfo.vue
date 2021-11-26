<template>
    <body>
        <Header />
        <main>
            <h2>Update login info</h2>
        </main>
    </body>
</template>

<script>
import Header from "./Header.vue";
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
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
    name : "UpdateLoginInfo",
    components : {
        Header
    },
    created() {
    axios_instance
      .get("/librarian/{id}")
      .then(response => {
        this.librarian = response.data;
        console.log(response.data);
      })
      .catch(error => console.log(error));
  },
  data() {
    return {
      librarian: []
    };
  }
}
</script>
