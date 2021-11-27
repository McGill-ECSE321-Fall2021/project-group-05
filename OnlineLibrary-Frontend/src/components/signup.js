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
      inPersonMemberId: "",
      inPersonMemberEmailAddress: "",
      inPersonMemberUsername: "",
      inPersonMemberPassword: "",
      inPersonMemberPasswordConfirmation: "",
      inPersonMemberErrorMsg: "",
      newMemberFullName: "",
      newMemberPhysicalAddress: "",
      newMemberEmailAddress: "",
      newMemberUsername: "",
      newMemberPassword: "",
      newMemberPasswordConfirmation: "",
      newMemberErrorMsg: ""
    };
  },
  methods: {
    isInPersonMemberInfoValid() {
      return true;
    },
    submitInPersonMemberForm(event) {
      event.preventDefault();
      console.log("Existing member");
    },
    isNewMemberInfoValid() {
      return true;
    },
    submitNewMemberForm(event) {
      event.preventDefault();
      console.log("New member");
    }
  }
}
