<template>
  <body>
    <Header />
    <main>
      <h1>Manage members</h1>
      <p class="error-message" v-if="errorMsg">{{ errorMsg }}</p>

      <!-- Popup to create a new member -->
      <!-- https://bootstrap-vue.org/docs/components/modal#prevent-closing -->
      <b-button v-b-modal.new-member-modal> New member </b-button>
      <b-modal
        id="new-member-modal"
        title="Register new member"
        @show="resetNewMemberModal"
        @hidden="resetNewMemberModal"
        @ok="handleOkNewMemberModal"
      >
        <b-form
          ref="newMemberForm"
          @submit.stop.prevent="handleSubmitNewMemberModal"
        >
          <b-form-group>
            <b-input
              type="text"
              placeholder="Full name"
              v-model="newMemberFullName"
              :state="
                submitAttempted ? newMemberFullName.trim().length > 0 : null
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
              placeholder="Physical address"
              v-model="newMemberPhysicalAddress"
              :state="submitAttempted ? newMemberPhysicalAddress.trim().length > 0 : null"
              required
            />
            <b-form-invalid-feedback>
              Address is required.
            </b-form-invalid-feedback>
          </b-form-group>
          <b-checkbox v-model="newMemberIsCitizen">
            Member is a citizen
          </b-checkbox>
        </b-form>
      </b-modal>

      <table>
        <tr>
          <th>ID</th>
          <th>Full name</th>
          <th>Address</th>
          <th>Email address</th>
          <th>Username</th>
          <th>Fee</th>
          <th>Status</th>
          <th></th>
        </tr>
        <tr v-for="member in members" :key="member.id">
          <td>{{ member.id }}</td>
          <td>{{ member.fullName }}</td>
          <td>{{ member.address }}</td>
          <td>
            <p v-if="member.onlineAccount">{{ member.onlineAccount.emailAddress }}</p>
            <p v-else> -- </p>
          </td>
          <td>
            <p v-if="member.onlineAccount">{{ member.onlineAccount.username }}</p>
            <p v-else> -- </p>
          </td>
          <td>{{ centsToDollars(member.totalFee) }}</td>
          <td>{{ titleCase(member.status) }}</td>
          <td>
            <button
              v-if="member.status === 'INACTIVE'"
              type="button"
              class="btn btn-primary"
              v-on:click="activateMember(member.id)"
            >
              Activate
            </button>
            <div v-else>
              <button
                type="button"
                class="btn btn-danger"
                v-bind:disabled="
                  !['GREEN', 'YELLOW', 'RED'].includes(member.status)
                "
                v-on:click="applyPenalty(member.id)"
              >
                Penalty
              </button>
              <button
                type="button"
                class="btn btn-success"
                v-bind:disabled="
                  !['YELLOW', 'RED', 'BLACKLISTED'].includes(member.status)
                "
                v-on:click="removePenalty(member.id)"
              >
                Undo penalty
              </button>
            </div>
          </td>
          <!-- TODO: Let librarian apply/remove status penalties and activate account -->
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
    AXIOS.get("/member/all")
      .then((response) => {
        this.members = response.data;
        this.errorMsg = "";
      })
      .catch((error) => {
        this.errorMsg =
          "Oops! 🙁 Something bad happened on our side. Try again later";
      });
  },
  data() {
    return {
      members: [],
      errorMsg: "",
      newMemberFullName: "",
      newMemberPhysicalAddress: "",
      newMemberIsCitizen: false,
      submitAttempted: false
    };
  },
  methods: {
    centsToDollars(cents) {
      return "$" + (cents / 100).toFixed(2);
    },
    titleCase(str) {
      return str.charAt(0).toUpperCase() + str.substring(1).toLowerCase();
    },
    activateMember(id) {
      const relativeUrl = "/member/" + id + "/activate";
      this.doPutRequest(relativeUrl, "Unable to activate member account. Try again later.", id);
    },
    applyPenalty(id) {
      const relativeUrl = "/member/" + id + "/applyPenalty";
      this.doPutRequest(relativeUrl, "Unable to apply penalty. Try again later.", id);
    },
    removePenalty(id) {
      const relativeUrl = "/member/" + id + "/removePenalty";
      this.doPutRequest(relativeUrl, "Unable to undo penalty. Try again later.", id);
    },
    doPutRequest(relativeUrl, errorMsg, id) {
      const self = this;
      AXIOS.put(relativeUrl)
        .then((response) => {
          if (response.status === 200) {
            const index = self.members.findIndex((member) => member.id === id);
            // Need to use self.$set to trigger update in reactivity system
            self.$set(self.members, index, response.data);
            this.errorMsg = "";
          } else {
            self.errorMsg = errorMsg;
          }
        })
        .catch((error) => {
          self.errorMsg = errorMsg;
        });
    },

    // Form in modal based on https://bootstrap-vue.org/docs/components/modal#prevent-closing
    registerNewMember() {
      const self = this;
      AXIOS.post("/member/", {
        fullName: self.newMemberFullName,
        address: self.newMemberPhysicalAddress,
        isCitizen: self.newMemberIsCitizen
      })
      .then((response) => {
        if (response.status === 200) {
          const createdMember = response.data;
          self.members.push(createdMember);
          self.activateMember(createdMember.id);
        }
        else {
          self.errorMsg = "Unable to create member. Try again later.";
        }
      })
      .catch((error) => {
        self.errorMsg = "Unable to create member. Try again later.";
      });
    },
    checkNewMemberFormValidity() {
      this.submitAttempted = true;
      return this.newMemberFullName.trim().length > 0 &&
             this.newMemberPhysicalAddress.trim().length > 0;
      // this.$refs.newMemberForm.checkValidity() would be nicer, but it seems to allow invalid values (e.g. all whitespace)
    },
    resetNewMemberModal() {
      this.submitAttempted = false;
      this.newMemberFullName = "";
      this.newMemberPhysicalAddress = "";
      this.newMemberIsCitizen = false;
    },
    handleOkNewMemberModal(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      // Trigger submit handler
      this.handleSubmitNewMemberModal();
    },
    handleSubmitNewMemberModal() {
      // Exit when the form isn't valid
      if (!this.checkNewMemberFormValidity()) {
        return;
      }
      // Create the new member
      this.registerNewMember();
      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide("new-member-modal");
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
