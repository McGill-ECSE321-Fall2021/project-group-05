import axios from 'axios';

var config = require('../../config')

const backendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.backendHost}:${config.build.backendPort}`
  : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl = (process.env.NODE_ENV === "production")
  ? `http://${config.build.host}:${config.build.port}`
  : `http://${config.dev.host}:${config.dev.port}`;
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

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
    }
  },
  methods: {
    doLogInMember: function (username, password) {
      var self = this;
      AXIOS.get('/member/login', {
        params: {
          username,
          password
        }
      })
        .then(function (response) {
          // Store stringified member info in session storage and redirect to member home page
          if (response.status === 200) {
            sessionStorage.setItem("loggedInMember", JSON.stringify(response.data))
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInLibrarian")
            // TODO: Get url of member home page
            window.location.href = "/#/home/member"
          }
          // TODO: Do actual error handling
          else {
            self.memberErrorMsg = "Invalid username or password."
          }
        })
        // TODO: Do actual error handling
        .catch(error => self.memberErrorMsg = "Invalid username or password.")
    },
    doLogInLibrarian(username, password) {
      var self = this;
      AXIOS.get('/librarian/login', {
        params: {
          username,
          password
        }
      })
        .then(function (response) {
          // Store stringified member info in session storage and redirect to member home page
          if (response.status === 200) {
            sessionStorage.setItem("loggedInLibrarian", JSON.stringify(response.data))
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInMember")
            // TODO: Get url of librarian home page
            window.location.href = "/#/home/librarian"
          }
          // TODO: Do actual error handling
          else {
            self.librarianErrorMsg = "Invalid username or password."
          }
        })
        // TODO: Do actual error handling
        .catch(error => self.librarianErrorMsg = "Invalid username or password.")
    }
  }
}
