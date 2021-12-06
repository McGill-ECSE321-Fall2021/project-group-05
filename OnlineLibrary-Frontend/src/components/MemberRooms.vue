<template>
  <body>
    <Header />
    <main>
      <h1>Rooms</h1>
      <table>
        <tr>
          <th>Room ID</th>
          <th>Room Name</th>
          <th>Room Capacity</th>
        </tr>
        <tr v-for="room in rooms" :key="room.id">
          <td>{{ room.id }}</td>
          <td>
            <router-link :to="{ name: 'MemberRoom', params: { roomId: room.id } }">{{
              room.name
            }}</router-link>
          </td>
          <td>{{ room.capacity }}</td>
        </tr>
      </table>
      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </main>
  </body>
</template>

<script>
import Header from "./MemberHeader.vue";
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

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "MemberRooms",
  components: {
    Header
  },
  created() {
    axios_instance
      .get("/room/")
      .then(response => {
        this.rooms = response.data;
        this.errorMessage = "";
      })
      .catch(error => {
        this.errorMessage =
          "Oops! 🙁 Something bad happened on our side. Try again later";
      });
  },
  data() {
    return {
      rooms: [],
      errorMessage: ""
    };
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
