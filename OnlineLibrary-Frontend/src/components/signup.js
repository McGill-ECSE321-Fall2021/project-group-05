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
      if (!this.isUsernameTaken()) {
        this.createOnlineAccount();
      }
    },
    isUsernameTaken() {
      // TODO Add this once there's an endpoint for getting member by username
      return false;
    },
    createOnlineAccount() {
      const relativeUrl = '/member/' + this.memberId + '/onlineAccount';
      const self = this;
      AXIOS.post(relativeUrl, {
        username: self.username,
        emailAddress: self.emailAddress,
        password: self.password
      })
        .then((response) => {
          if (response.status === 200) {
            sessionStorage.setItem("loggedInMember", JSON.stringify(response.data));
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInLibrarian");
            self.$router.push({ name: "MemberHome" });
          }
          // TODO: Do real exception handling once the backend returns useful errors
          else {
            self.memberErrorMsg = "Unable to sign up. Please double-check your library ID.";
          }
        })
        // TODO: Do real exception handling once the backend returns useful errors
        .catch((error) => {
          self.memberErrorMsg = "Unable to sign up. Please double-check your library ID.";
        });
    },
    submitNewMemberForm(event) {
      event.preventDefault();
      if (!this.isUsernameTaken()) {
        this.createNewMember();
      }
    },
    createNewMember() {
      const relativeUrl = '/member/';
      const self = this;
      AXIOS.post(relativeUrl, {
        fullName: self.fullName,
        address: self.physicalAddress,
        isCitizen: false,
        onlineAccount: {
          username: self.username,
          emailAddress: self.emailAddress,
          password: self.password
        }
      })
        .then((response) => {
          if (response.status === 200) {
            self.$router.push({ name: "Inactive" });
          }
          // TODO: Do real exception handling once the backend returns useful errors
          else {
            self.memberErrorMsg = "Unable to sign up. Please try again in a few minutes.";
          }
        })
        // TODO: Do real exception handling once the backend returns useful errors
        .catch((error) => {
          self.memberErrorMsg = "Unable to sign up. Please try again in a few minutes.";
        });
    }
  }
}
