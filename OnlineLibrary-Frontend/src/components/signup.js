import axios from 'axios';

const config = require('../../config');

const backendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.backendHost}`
  : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.host}`
  : `http://${config.dev.host}:${config.dev.port}`;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  name: 'Signup',
  data() {
    return {
      hasInPersonAccount: false,
      fullName: "",
      physicalAddress: "",
      memberId: "",
      emailAddress: "",
      username: "",
      password: "",
      passwordConfirmation: "",
      errorMsg: ""
    };
  },
  methods: {
    submitInPersonMemberForm(event) {
      event.preventDefault();
      console.log("In person member");
    },
    submitNewMemberForm(event) {
      event.preventDefault();
      console.log("New member");
    }
  }
}
