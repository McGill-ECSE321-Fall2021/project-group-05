<template>
  <body>
    <Header />
    <main class="form">
      <h1>Update personal info</h1>
      <b-form @submit="onSubmit" v-if="show">
        <b-form-group
          id="input-group-1"
          label="Your new name:"
          label-for="input-1"
        >
          <b-form-input
            id="input-1"
            v-model="formName"
            :placeholder="name"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group
          id="input-group-3"
          label="Your new address:"
          label-for="input-3"
        >
          <b-form-input
            id="input-3"
            type="text"
            v-model="formAddress"
            :placeholder="address"
            required
          ></b-form-input>
        </b-form-group>
        <b-button v-on:click="onSubmit" variant="primary" v-bind:disabled="!this.formName.trim() || !this.formAddress.trim()">Submit</b-button>
      </b-form>
      <p v-if="errorMessage" class="error-message">
        ERROR: {{ this.errorMessage }}
      </p>
      <p v-if="confirmationMessage" class="confirmation-message">
        {{ this.confirmationMessage }}
      </p>
    </main>
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
      name: "",
      address: "",
      member: "",
      formName: "",
      formAddress: "",
      errorMessage: "",
      confirmationMessage: "",
      show: true,
    };
  },
  created: function () {
    const member = JSON.parse(sessionStorage.getItem("loggedInMember")).member;
    const id = member.id;
    const relativeURL = "/member/" + id + "/";
    const self = this;
    axios_instance
      .get(relativeURL)
      .then((response) => {
        self.address = response.data.address;
        self.name = response.data.fullName;
        self.formName = response.data.fullName;
        self.formAddress = response.data.address;
      })
      .catch((error) => {
        console.log(error);
        self.errorMessage = "There seems to be an error, please log out and log back in";
        });
  },

  methods: {
    onSubmit() {
      const member = JSON.parse(
        sessionStorage.getItem("loggedInMember")
      ).member;
      const self = this;
      axios_instance
        .put(
          `/member/${member.id}`,
          {},
          {
            params: {
              address: self.formAddress,
              fullName: self.formName,
              id: member.id,
            },
          }
        )
        .then((response) => {
          self.errorMessage = "";
          const updateMember = response.data;
          self.address= updateMember.address;
          self.name= updateMember.fullName;
          self.confirmationMessage = "Your info has been updated 😊";
        })
        .catch((error) => {
          self.errorMessage = "Could not update your info, please try again.";
        });
    },
  },
};
</script>

<style scoped>
.error-message {
  color: red;
}
.confirmation-message {
  color: green;
}
</style>
