<template>
  <body>
    <Header />
    <main>
      <h1>View all members</h1>
      <table>
        <tr>
          <th>ID</th>
          <th>Full name</th>
          <th>Address</th>
          <th>Email address</th>
          <th>Username</th>
          <th>Fee</th>
          <th>Status</th>
        </tr>
        <tr v-for="member in members" :key="member.id">
          <td>{{ member.id }}</td>
          <td>{{ member.fullName }}</td>
          <td>{{ member.address }}</td>
          <td>{{ member.onlineAccount.emailAddress }}</td>
          <td>{{ member.onlineAccount.username }}</td>
          <td>{{ member.totalFee }}</td>
          <td>{{ titleCase(member.status) }}</td>
          <!-- TODO: Let librarian apply/remove status penalties and activate account -->
        </tr>
      </table>
      <p class="error-message" v-if="errorMsg">{{ errorMsg }}</p>
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
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "LibrarianManageMembers",
  components: {
    Header
  },
  created: function() {
    console.log("Created");
    AXIOS.get("/member/all")
      .then((response) => {
        this.members = response.data;
        this.errorMessage = "";
      })
      .catch((error) => {
        this.errorMessage =
          "Oops! 🙁 Something bad happened on our side. Try again later";
      });
  },
  data() {
    return {
      members: [],
      errorMsg: ""
    };
  },
  methods: {
    titleCase(str) {
      return str.charAt(0).toUpperCase() + str.substring(1).toLowerCase();
    }
  }
};
</script>

<style scoped>
.error-message {
  margin: 20px;
  text-align: center;
  color: red;
}
</style>
