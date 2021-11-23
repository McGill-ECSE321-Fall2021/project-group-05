<template>
  <main>
    <Header />
    <h2>Rooms</h2>
    <table>
      <tr>
        <th>Room ID</th>
        <th>Room Name</th>
        <th>Room Capacity</th>
      </tr>
      <tr v-for="room in rooms" :key="room.id">
        <td>{{ room.id }}</td>
        <td>{{ room.name }}</td>
        <td>{{ room.capacity }}</td>
      </tr>
    </table>
  </main>
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
  name: "Rooms",
  components: {
    Header
  },
  created: function() {
    axios_instance
      .get("/room/")
      .then(response => {
        this.rooms = response.data;
        console.log(response.data);
      })
      .catch(error => console.log(error));
  },
  data() {
    return {
      rooms: []
    };
  }
};
</script>
