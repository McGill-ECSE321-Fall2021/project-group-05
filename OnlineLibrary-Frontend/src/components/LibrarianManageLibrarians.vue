<template>
  <body>
    <Header />
    <main>
      <h1>Manage librarians</h1>
      <p class="error-message" v-if="errorMsg">{{ errorMsg }}</p>
      <!-- Popup to confirm deletion -->
      <b-modal v-model="showConfirmationModal" title="Confirm deletion">
        <p>
          Are you sure you would like to delete librarian
          {{ librarianToDelete.username }}?
        </p>
        <template #modal-footer>
          <b-button v-on:click="closeConfirmDeleteModal()">Cancel</b-button>
          <b-button
            v-on:click="
              deleteLibrarian();
              closeConfirmDeleteModal();
            "
            variant="danger"
          >
            Confirm
          </b-button>
        </template>
      </b-modal>

      <!-- Popup to create a new librarian -->
      <!-- https://bootstrap-vue.org/docs/components/modal#prevent-closing -->
      <b-button v-b-modal.new-librarian-modal> New librarian </b-button>
      <b-modal
        id="new-librarian-modal"
        title="Register new librarian"
        @show="resetNewLibrarianModal"
        @hidden="resetNewLibrarianModal"
        @ok="handleOkNewLibrarianModal"
      >
        <b-form
          ref="newLibrarianForm"
          @submit.stop.prevent="handleSubmitNewLibrarianModal"
        >
          <b-form-group>
            <b-input
              type="text"
              placeholder="Full name"
              v-model="newLibrarianFullName"
              :state="
                submitAttempted ? newLibrarianFullName.trim().length > 0 : null
              "
              required
            />
            <b-form-invalid-feedback>
              Full name is required.
            </b-form-invalid-feedback>
          </b-form-group>
          <b-form-group>
            <b-input
              type="text"
              placeholder="Username"
              v-model="newLibrarianUsername"
              @keydown.space.prevent
              :state="submitAttempted ? newLibrarianUsername.length > 0 : null"
              required
            />
            <b-form-invalid-feedback>
              Username is required.
            </b-form-invalid-feedback>
          </b-form-group>
          <b-form-group>
            <b-input
              type="password"
              placeholder="Password"
              v-model="newLibrarianPassword"
              @keydown.space.prevent
              :state="submitAttempted ? newLibrarianPassword.length >= 8 : null"
              required
            />
            <b-form-invalid-feedback>
              Password must be at least 8 characters.
            </b-form-invalid-feedback>
          </b-form-group>
          <b-form-group>
            <b-input
              type="password"
              placeholder="Confirm password"
              v-model="newLibrarianPasswordConfirmation"
              @keydown.space.prevent
              :state="
                submitAttempted
                  ? newLibrarianPasswordConfirmation === newLibrarianPassword
                  : null
              "
              required
            />
            <b-form-invalid-feedback>
              Passwords must match.
            </b-form-invalid-feedback>
          </b-form-group>
        </b-form>
      </b-modal>

      <table>
        <tr>
          <th>Full name</th>
          <th>Username</th>
          <th></th>
        </tr>
        <tr v-for="librarian in librarians" :key="librarian.id">
          <td>{{ librarian.fullName }}</td>
          <td>{{ librarian.username }}</td>
          <td>
            <b-button
              v-on:click="
                librarianToDelete = librarian;
                showConfirmationModal = !showConfirmationModal;
              "
              variant="danger"
            >
              Delete
            </b-button>
          </td>
          <!-- TODO: Let head librarian delete librarians -->
        </tr>
      </table>
    </main>
  </body>
</template>

<script>
import Header from "./LibrarianHeader.vue";
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "LibrarianManageMembers",
  components: {
    Header,
  },
  created: function () {
    console.log("Created");
    AXIOS.get("/librarian/all")
      .then((response) => {
        this.librarians = response.data.filter(
          (librarian) => !librarian.isHead
        );
        this.errorMsg = "";
      })
      .catch((error) => {
        this.errorMsg =
          "Oops! 🙁 Something bad happened on our side. Try again later.";
      });
  },
  data() {
    return {
      librarians: [],
      librarianToDelete: {},
      errorMsg: "",
      showConfirmationModal: false,
      showNewLibrarianModal: false,
      newLibrarianFullName: "",
      newLibrarianUsername: "",
      newLibrarianPassword: "",
      newLibrarianPasswordConfirmation: "",
      submitAttempted: false,
    };
  },
  methods: {
    closeConfirmDeleteModal() {
      this.showConfirmationModal = false;
      this.librarianToDelete = {};
    },
    deleteLibrarian() {
      const id = this.librarianToDelete.id;
      const relativeUrl = "/librarian/" + id;
      AXIOS.delete(relativeUrl)
        .then((response) => {
          if (response.status === 200) {
            this.librarians = this.librarians.filter(
              (librarian) => librarian.id !== id
            );
          } else {
            this.error = "Unable to delete librarian. Try again later.";
          }
        })
        .catch((error) => {
          this.error = "Unable to delete librarian. Try again later.";
        });
    },
    // Form in modal based on https://bootstrap-vue.org/docs/components/modal#prevent-closing
    registerLibrarian() {
      console.log("Registering new librarian");
      const self = this;
      AXIOS.post(
        "/librarian/",
        {},
        {
          params: {
            fullName: self.newLibrarianFullName,
            username: self.newLibrarianUsername,
            password: self.newLibrarianPassword,
          },
        }
      )
      .then((response) => {
        if (response.status === 200) {
          self.librarians.push(response.data);
        }
        else {
          self.errorMsg = "Unable to create librarian. Try again later.";
        }
      })
      .catch((error) => {
        self.errorMsg = "Unable to create librarian. Try again later.";
      });
    },
    checkNewLibrarianFormValidity() {
      console.log("Checking form validity.");
      this.submitAttempted = true;
      return this.newLibrarianFullName.trim().length > 0 &&
             this.newLibrarianUsername.length > 0 &&
             this.newLibrarianPassword.length >= 8 &&
             this.newLibrarianPasswordConfirmation === this.newLibrarianPassword;
      // this.$refs.newLibrarianForm.checkValidity() would be nicer, 
      // but it's allowing invalid passwords in some cases (e.g. "pass")
    },
    resetNewLibrarianModal() {
      console.log("Resetting modal.");
      this.submitAttempted = false;
      this.newLibrarianFullName = "";
      this.newLibrarianUsername = "";
      this.newLibrarianPassword = "";
      this.newLibrarianPasswordConfirmation = "";
    },
    handleOkNewLibrarianModal(bvModalEvt) {
      console.log("Handling ok");
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      // Trigger submit handler
      this.handleSubmitNewLibrarianModal();
    },
    handleSubmitNewLibrarianModal() {
      console.log("Handling submit.");
      // Exit when the form isn't valid
      if (!this.checkNewLibrarianFormValidity()) {
        return;
      }
      // Create the new librarian
      this.registerLibrarian();
      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide("new-librarian-modal");
      });
    },
  },
};
</script>

<style scoped>
.error-message {
  margin: 20px;
  text-align: center;
  color: red;
}
</style>
