<template>
  <body>
    <Header />
    <main>
      <h1>Room</h1>
      <h2>{{ this.room.name }}</h2>
      <hr />
      <dl>
        <dt>Id</dt>
        <dd>{{ this.room.id }}</dd>
        <dt>Name</dt>
        <dd>{{ this.room.name }}</dd>
        <dt>Capacity</dt>
        <dd>{{ this.room.capacity }}</dd>
      </dl>
    </main>
  </body>
</template>

<script>
import Header from "../components/MemberHeader.vue";
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
  name: "MemberRoom",
  data() {
    return {
      room: {
        id: NaN,
        name: "",
        capacity: NaN
      }
    };
  },
  components: {
    Header
  },
  created() {
    axios_instance
      .get(`/room/${this.$route.params.roomId}`)
      .then(response => {
        this.room = response.data;
      })
      .catch(error => {
        console.log(error);
        this.$router.replace({ name: "NotFound" });
      });
  },
  beforeRouteUpdate(to, from, next) {
    console.log("Updating route...");
    axios_instance
      .get(`/room/${to.params.roomId}`)
      .then(response => {
        this.room = response.data;
        next();
      })
      .catch(error => {
        console.log(error);
        next({ name: "NotFound" });
      });
  }
};
</script>
