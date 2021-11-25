import axios from 'axios';

const config = require('../../config');

const backendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.backendHost}:${config.build.backendPort}`
  : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.host}:${config.build.port}`
  : `http://${config.dev.host}:${config.dev.port}`;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  name: 'login',
  data() {
    return {
      memberUsername: '',
      memberPassword: '',
      memberErrorMsg: '',
      librarianUsername: '',
      librarianPassword: '',
      librarianErrorMsg: ''
    };
  },
  methods: {
    doLogInMember(username, password) {
      const self = this;
      AXIOS.get('/member/login', {
        params: {
          username,
          password
        }
      })
        .then((response) => {
          // Store stringified member info in session storage and redirect to member home page
          if (response.status === 200) {
            sessionStorage.setItem("loggedInMember", JSON.stringify(response.data));
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInLibrarian");
            self.$router.push({ name: "MemberHome" });
          }
          // TODO: Do actual error handling
          else {
            console.log("Response status: " + response.status.toString());
            self.memberErrorMsg = "Invalid username or password.";
          }
        })
        // TODO: Do actual error handling
        .catch((error) => {
          console.log(error);
          self.memberErrorMsg = "Invalid username or password.";
        })
    },
    doLogInLibrarian(username, password) {
      const self = this;
      AXIOS.get('/librarian/login', {
        params: {
          username,
          password
        }
      })
        .then((response) => {
          // Store stringified member info in session storage and redirect to member home page
          if (response.status === 200) {
            sessionStorage.setItem("loggedInLibrarian", JSON.stringify(response.data));
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInMember");
            self.$router.push("/home/librarian");
          }
          // TODO: Do actual error handling
          else {
            self.librarianErrorMsg = "Invalid username or password.";
          }
        })
        // TODO: Do actual error handling
        .catch((error) => self.librarianErrorMsg = "Invalid username or password.")
    }
  }
}
