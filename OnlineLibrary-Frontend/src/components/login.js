import axios from 'axios';

const config = require('../../config');

const backendUrl = (process.env.NODE_ENV === "production")
  ? `https://${config.build.backendHost}`
  : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl = (process.env.NODE_ENV === "production")
  ? `https://${config.build.host}`
  : `http://${config.dev.host}:${config.dev.port}`;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

export default {
  name: 'Login',
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
  created() {
    const loggedInMember = sessionStorage.getItem("loggedInMember");
    const loggedInLibrarian = sessionStorage.getItem("loggedInLibrarian");
    // Both logged in
    if (loggedInMember && loggedInLibrarian) {
      // Do nothing
    }
    // Member already logged in
    else if (loggedInMember) {
      this.$router.replace({ name: "MemberHome" });
    }
    // Librarian already logged in
    else if (loggedInLibrarian) {
      this.$router.replace({ name: "LibrarianHome" });
    }
  },
  methods: {
    submitMemberForm(event) {
      event.preventDefault();
      this.doLogInMember(this.memberUsername, this.memberPassword);
    },
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
          const member = response.data.member;
          if (member.status === 'INACTIVE') {
            self.$router.replace({ name: "Inactive" });
          }
          else if (member.status === 'BLACKLISTED') {
            self.$router.replace({ name: "Blacklisted" });
          }
          else {
            sessionStorage.setItem("loggedInMember", JSON.stringify(response.data));
            // Prevent 2 users from being logged in at once
            sessionStorage.removeItem("loggedInLibrarian");
            self.$router.push({ name: "MemberHome" });
          }
        })
        // TODO: Do actual error handling
        .catch((error) => {
          self.memberErrorMsg = "Invalid username or password.";
        })
    },
    submitLibrarianForm(event) {
      event.preventDefault();
      this.doLogInLibrarian(this.librarianUsername, this.librarianPassword);
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
          sessionStorage.setItem("loggedInLibrarian", JSON.stringify(response.data));
          // Prevent 2 users from being logged in at once
          sessionStorage.removeItem("loggedInMember");
          self.$router.push({ name: "LibrarianHome" });
        })
        // TODO: Do actual error handling
        .catch((error) => self.librarianErrorMsg = "Invalid username or password.")
    }
  }
}
