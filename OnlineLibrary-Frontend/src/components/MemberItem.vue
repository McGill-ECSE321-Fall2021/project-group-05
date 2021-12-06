<template>
  <body>
    <Header />
    <main v-if="this.item.type === 'Book'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
        <div class="item-description-container">
          <img src="../assets/book.svg" alt="Book cover" />
          <dl>
            <dt>Id</dt>
            <dd>{{ this.item.id }}</dd>
            <dt>Title</dt>
            <dd>{{ this.item.title }}</dd>
            <dt>Author</dt>
            <dd>{{ this.item.author }}</dd>
            <dt>Number of pages</dt>
            <dd>{{ this.item.numberOfPage }}</dd>
            <dt>ISBN</dt>
            <dd>{{ this.item.isbn }}</dd>
          </dl>
        </div>
        <div class="item-actions-container">
          <b-button variant="primary" @click="reserve">Reserve</b-button>
          <p class="success-message" v-if="reservationSuccessMessage">
            {{ reservationSuccessMessage }}
          </p>
          <p class="error-message" v-if="reservationErrorMessage">
            {{ reservationErrorMessage }}
          </p>
        </div>
      </div>
      <h2>Item copies</h2>
      <table>
        <tr>
          <th>Id</th>
          <th>Status</th>
        </tr>
        <tr v-for="copy in copiesWithStatus" :key="copy.id">
          <td>{{ copy.id }}</td>
          <td>{{ copy.status }}</td>
        </tr>
      </table>
    </main>
    <main v-else-if="this.item.type === 'Album'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
        <div class="item-description-container">
          <img src="../assets/album.svg" alt="Album cover" />
          <dl>
            <dt>Id</dt>
            <dd>{{ this.item.id }}</dd>
            <dt>Title</dt>
            <dd>{{ this.item.title }}</dd>
            <dt>Composer/performer</dt>
            <dd>{{ this.item.composerPerformer }}</dd>
            <dt>Genre</dt>
            <dd>{{ this.item.genre }}</dd>
          </dl>
        </div>
        <div class="item-actions-container">
          <b-button variant="primary" @click="reserve">Reserve</b-button>
          <p class="success-message" v-if="reservationSuccessMessage">
            {{ reservationSuccessMessage }}
          </p>
          <p class="error-message" v-if="reservationErrorMessage">
            {{ reservationErrorMessage }}
          </p>
        </div>
      </div>
      <h2>Item copies</h2>
      <table>
        <tr>
          <th>Id</th>
          <th>Status</th>
        </tr>
        <tr v-for="copy in copiesWithStatus" :key="copy.id">
          <td>{{ copy.id }}</td>
          <td>{{ copy.status }}</td>
        </tr>
      </table>
    </main>
    <main v-else-if="this.item.type === 'Archive'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="item-description-container">
        <img src="../assets/archive.svg" alt="Archive box" />
        <dl>
          <dt>Id</dt>
          <dd>{{ this.item.id }}</dd>
          <dt>Title</dt>
          <dd>{{ this.item.title }}</dd>
          <dt>Description</dt>
          <dd>{{ this.item.description }}</dd>
          <dt>Publication date</dt>
          <dd>{{ this.item.publicationDate }}</dd>
        </dl>
      </div>
      <h2>Item copies</h2>
      <table>
        <tr>
          <th>Id</th>
          <th>Status</th>
        </tr>
        <tr v-for="copy in copiesWithStatus" :key="copy.id">
          <td>{{ copy.id }}</td>
          <td>{{ copy.status }}</td>
        </tr>
      </table>
    </main>
    <main v-else-if="this.item.type === 'Movie'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
        <div class="item-description-container">
          <img src="../assets/movie.svg" alt="Movie icon" />
          <dl>
            <dt>Id</dt>
            <dd>{{ this.item.id }}</dd>
            <dt>Title</dt>
            <dd>{{ this.item.title }}</dd>
            <dt>Director</dt>
            <dd>{{ this.item.director }}</dd>
            <dt>Genre</dt>
            <dd>{{ this.item.genre }}</dd>
            <dt>Duration</dt>
            <dd>{{ this.item.length }}</dd>
          </dl>
        </div>
        <div class="item-actions-container">
          <b-button variant="primary" @click="reserve">Reserve</b-button>
          <p class="success-message" v-if="reservationSuccessMessage">
            {{ reservationSuccessMessage }}
          </p>
          <p class="error-message" v-if="reservationErrorMessage">
            {{ reservationErrorMessage }}
          </p>
        </div>
      </div>
      <h2>Item copies</h2>
      <table>
        <tr>
          <th>Id</th>
          <th>Status</th>
        </tr>
        <tr v-for="copy in copiesWithStatus" :key="copy.id">
          <td>{{ copy.id }}</td>
          <td>{{ copy.status }}</td>
        </tr>
      </table>
    </main>
    <main v-else-if="this.item.type === 'Newspaper'">
      <h1>{{ this.item.periodicalTitle }}</h1>
      <hr />
      <div class="item-description-container">
        <img src="../assets/newspaper.svg" alt="Newspaper" />
        <dl>
          <dt>Id</dt>
          <dd>{{ this.item.id }}</dd>
          <dt>Title of the periodical</dt>
          <dd>{{ this.item.periodicalTitle }}</dd>
          <dt>Date</dt>
          <dd>{{ this.item.date }}</dd>
          <dt>Frequency</dt>
          <dd>{{ this.item.frequency }}</dd>
          <dt>Publication number</dt>
          <dd>{{ this.item.number }}</dd>
        </dl>
      </div>
      <h2>Item copies</h2>
      <table>
        <tr>
          <th>Id</th>
          <th>Status</th>
        </tr>
        <tr v-for="copy in copiesWithStatus" :key="copy.id">
          <td>{{ copy.id }}</td>
          <td>{{ copy.status }}</td>
        </tr>
      </table>
    </main>
  </body>
</template>

<script>
import Header from "./MemberHeader.vue";
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

export default {
  name: "MemberItem",
  data() {
    return {
      item: {},
      copies: [],
      copiesWithStatus: [],
      reservationSuccessMessage: "",
      reservationErrorMessage: ""
    };
  },
  components: {
    Header
  },
  created() {
    const promiseToFetchItem = this.fetchItemDetails(
      this.$route.params.itemId
    ).catch(error => {
      this.$router.replace({ name: "NotFound" });
    });

    const promisetoFetchCopies = this.fetchItemCopies(
      this.$route.params.itemId
    ).catch(error => {
      this.$router.replace({ name: "NotFound" });
    });

    Promise.all([promiseToFetchItem, promisetoFetchCopies]).then(
      this.fetchCopiesWithStatus
    );
  },
  beforeRouteUpdate(to, from, next) {
    const promiseToFetchItem = this.fetchItemDetails(to.params.itemId).catch(
      error => {
        next({ name: "NotFound" });
      }
    );
    const promisetoFetchCopies = this.fetchItemDetails(to.params.itemId).catch(
      error => {
        next({ name: "NotFound" });
      }
    );

    Promise.all([promiseToFetchItem, promisetoFetchCopies]).then(
      this.fetchCopiesWithStatus
    );
  },
  methods: {
    reserve() {
      const storedCredentials = window.sessionStorage.getItem("loggedInMember");
      if (storedCredentials == null) {
        this.$router.push({ name: "Login" });
      }
      const loggedInMember = JSON.parse(storedCredentials);
      axios_instance
        .post(
          `/reservation`,
          {},
          {
            params: {
              memberId: loggedInMember.member.id,
              reservableItemId: this.item.id
            }
          }
        )
        .then(response => {
          this.reservationSuccessMessage =
            "You have successfully reserved this item!";
          this.reservationErrorMessage = "";
        })
        .catch(error => {
          this.reservationSuccessMessage = "";
          this.reservationErrorMessage = "You cannot reserve this item";
        });
    },
    fetchItemDetails(itemId) {
      return axios_instance.get(`/libraryItemInfo/${itemId}`).then(response => {
        this.item = response.data;
        this.newItem = { ...this.item };
      });
    },
    fetchItemCopies(itemId) {
      return axios_instance
        .get(`libraryItemInfo/${itemId}/libraryItem`)
        .then(response => {
          this.copies = response.data;
        });
    },
    fetchCopiesWithStatus() {
      if (this.item.type === "Archive" || this.item.type === "Newspaper") {
        this.copiesWithStatus = this.copies.map(copy => {
          return {
            ...copy,
            status: "On-premise"
          };
        });
      } else {
        Promise.all(
          this.copies.map(copy =>
            axios_instance
              .get(`/reservableItem/${copy.id}/loan`)
              .then(response => {
                if (response.data.length === 0) {
                  return { ...copy, status: "Available" };
                } else {
                  return { ...copy, status: "Checked out" };
                }
              })
          )
        ).then(responses => {
          this.copiesWithStatus = responses;
        });
      }
    }
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
}
.item-description-container {
  display: flex;
}
.item-description-container img {
  padding: 20px;
  width: 200px;
}
.item-actions-container {
  flex-grow: 0.5;
  text-align: center;
}
.success-message {
  color: green;
}
.error-message {
  color: red;
}
</style>
