<template>
  <body>
    <Header />
    <div class="form">
      <h1>Update personal info</h1>
      <b-form @submit="onSubmit" v-if="show">
        <p>Your current name: {{ currentName }}</p>
        <b-form-group id="input-group-1" label="Your new name:" label-for="input-1">
          <b-form-input
            id="input-1"
            style="width: 15%"
            v-model="name"
            placeholder="Enter name"
            required
          ></b-form-input>
        </b-form-group>
        <p>Your current address: {{ member.address }} </p>
        <b-form-group
          id="input-group-3"
          label="Your new address:"
          label-for="input-3"
        >

          <b-form-input
            id="input-3"
            style="width: 15%"
            type="text"
            v-model="address"
            placeholder="New address"
            required
          ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary">Submit</b-button>
      </b-form>
    </div>
  </body>
</template>

<script>
import Header from "./MemberHeader.vue";
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
  name: "MemberUpdateInfo",
  components: {
    Header,
  },
  data() {
    return {
      form: {
        name: "",
        address: "",
        oldAddress: "",
        currentName: "",
      },
      show: true,
    };
  },
  created: function () {
    const member = JSON.parse(
      sessionStorage.getItem("loggedInMember")
    ).member;
    const id = member.id;
    const relativeURL = "/member/" + id + "/";
    axios_instance
      .get(relativeURL)
      .then((response) => {
        this.member = response.data;
        this.id = this.$route.params.id;
        this.oldAddress = member.address;
        this.currentName = member.fullName;
        console.log(response.data);
      })
      .catch((error) => console.log(error));
  },

  methods: {
    onSubmit() {
      const member = JSON.parse(
        sessionStorage.getItem("loggedInMember")
      ).member;
      axios_instance
        .put(
          `/member/${member.id}`,
          {},
          {
            params: {
              address: this.address,
              fullName: this.name,
              id: member.id,
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const updateMember = response.data.id;
          this.$router.push({
            name: "member",
            params: { memberId: updateMember },
          });
        })
        .catch((error) => {
          this.errorMessage =
            "could not update your info, please try again";
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
