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
          <h2>Update item details</h2>
          <b-form @submit="updateBook">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Author" label-for="author">
              <b-form-input
                id="author"
                type="text"
                v-model="newItem.author"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Number of pages" label-for="number-of-pages">
              <b-form-input
                id="number-of-pages"
                type="number"
                v-model="newItem.numberOfPage"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="ISBN" label-for="isbn">
              <b-form-input
                id="isbn"
                type="number"
                v-model="newItem.isbn"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
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
      <b-button @click="addCopy">Add copy</b-button>
      <p class="success-message" v-if="addCopySuccessMessage">
        {{ addCopySuccessMessage }}
      </p>
      <p class="error-message" v-if="addCopyErrorMessage">
        {{ addCopyErrorMessage }}
      </p>
      <h2>Delete copy</h2>
      <b-form @submit="deleteCopy">
        <b-form-group label="Copy id" label-for="delete-copy-id">
          <b-form-select
            id="delete-copy-id"
            v-model="deleteCopyId"
            :options="copies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Delete copy</b-button>
        <p class="success-message" v-if="deleteCopySuccessMessage">
          {{ deleteCopySuccessMessage }}
        </p>
        <p class="error-message" v-if="deleteCopyErrorMessage">
          {{ deleteCopyErrorMessage }}
        </p>
      </b-form>
      <h2>Checkout copy</h2>
      <b-form @submit="checkOut">
        <b-form-group label="Copy id" label-for="checkout-copy-id">
          <b-form-select
            id="checkout-copy-id"
            v-model="checkOutCopyId"
            :options="availableCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-form-group label="Member id" label-for="checkout-member-id">
          <b-form-select
            id="checkout-member-id"
            v-model="checkOutMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Check out</b-button>
        <p class="success-message" v-if="checkOutSuccessMessage">
          {{ checkOutSuccessMessage }}
        </p>
        <p class="error-message" v-if="checkOutErrorMessage">
          {{ checkOutErrorMessage }}
        </p>
      </b-form>
      <h2>Return copy</h2>
      <b-form @submit="returnItem">
        <b-form-group label="Copy id" label-for="return-copy-id">
          <b-form-select
            id="return-copy-id"
            v-model="returnCopyId"
            :options="checkedOutCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Return</b-button>
        <p class="success-message" v-if="returnItemSuccessMessage">
          {{ returnItemSuccessMessage }}
        </p>
        <p class="error-message" v-if="returnItemErrorMessage">
          {{ returnItemErrorMessage }}
        </p>
      </b-form>
      <h2>Reserve item</h2>
      <b-form @submit="reserve">
        <b-form-group label="Member id" label-for="reserve-member-id">
          <b-form-select
            id="reserve-member-id"
            v-model="reserveMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Reserve</b-button>
        <p class="success-message" v-if="reserveSuccessMessage">
          {{ reserveSuccessMessage }}
        </p>
        <p class="error-message" v-if="reserveErrorMessage">
          {{ reserveErrorMessage }}
        </p>
      </b-form>
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
            <dt>Composer / Performer</dt>
            <dd>{{ this.item.composerPerformer }}</dd>
            <dt>Genre</dt>
            <dd>{{ this.item.genre }}</dd>
          </dl>
        </div>
        <div class="item-actions-container">
          <h2>Update item details</h2>
          <b-form @submit="updateAlbum">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group
              label="Composer / Performer"
              label-for="composer-performer"
            >
              <b-form-input
                id="composer-performer"
                type="text"
                v-model="newItem.composerPerformer"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Genre" label-for="genre">
              <b-form-input
                id="genre"
                type="text"
                v-model="newItem.genre"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
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
      <b-button @click="addCopy">Add copy</b-button>
      <p class="success-message" v-if="addCopySuccessMessage">
        {{ addCopySuccessMessage }}
      </p>
      <p class="error-message" v-if="addCopyErrorMessage">
        {{ addCopyErrorMessage }}
      </p>
      <h2>Delete copy</h2>
      <b-form @submit="deleteCopy">
        <b-form-group label="Copy id" label-for="delete-copy-id">
          <b-form-select
            id="delete-copy-id"
            v-model="deleteCopyId"
            :options="copies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Delete copy</b-button>
        <p class="success-message" v-if="deleteCopySuccessMessage">
          {{ deleteCopySuccessMessage }}
        </p>
        <p class="error-message" v-if="deleteCopyErrorMessage">
          {{ deleteCopyErrorMessage }}
        </p>
      </b-form>
      <h2>Checkout copy</h2>
      <b-form @submit="checkOut">
        <b-form-group label="Copy id" label-for="checkout-copy-id">
          <b-form-select
            id="checkout-copy-id"
            v-model="checkOutCopyId"
            :options="availableCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-form-group label="Member id" label-for="checkout-member-id">
          <b-form-select
            id="checkout-member-id"
            v-model="checkOutMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Check out</b-button>
        <p class="success-message" v-if="checkOutSuccessMessage">
          {{ checkOutSuccessMessage }}
        </p>
        <p class="error-message" v-if="checkOutErrorMessage">
          {{ checkOutErrorMessage }}
        </p>
      </b-form>
      <h2>Return copy</h2>
      <b-form @submit="returnItem">
        <b-form-group label="Copy id" label-for="return-copy-id">
          <b-form-select
            id="return-copy-id"
            v-model="returnCopyId"
            :options="checkedOutCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Return</b-button>
        <p class="success-message" v-if="returnItemSuccessMessage">
          {{ returnItemSuccessMessage }}
        </p>
        <p class="error-message" v-if="returnItemErrorMessage">
          {{ returnItemErrorMessage }}
        </p>
      </b-form>
      <h2>Reserve item</h2>
      <b-form @submit="reserve">
        <b-form-group label="Member id" label-for="reserve-member-id">
          <b-form-select
            id="reserve-member-id"
            v-model="reserveMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Reserve</b-button>
        <p class="success-message" v-if="reserveSuccessMessage">
          {{ reserveSuccessMessage }}
        </p>
        <p class="error-message" v-if="reserveErrorMessage">
          {{ reserveErrorMessage }}
        </p>
      </b-form>
    </main>
    <main v-else-if="this.item.type === 'Archive'">
      <h1>{{ this.item.title }}</h1>
      <hr />
      <div class="main-container">
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
        <div class="item-actions-container">
          <h2>Update item details</h2>
          <b-form @submit="updateArchive">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Description" label-for="description">
              <b-form-input
                id="description"
                type="text"
                v-model="newItem.description"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Publication date" label-for="publication-date">
              <b-form-input
                id="publication-date"
                type="date"
                v-model="newItem.publicationDate"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
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
      <b-button @click="addCopy">Add copy</b-button>
      <p class="success-message" v-if="addCopySuccessMessage">
        {{ addCopySuccessMessage }}
      </p>
      <p class="error-message" v-if="addCopyErrorMessage">
        {{ addCopyErrorMessage }}
      </p>
      <h2>Delete copy</h2>
      <b-form @submit="deleteCopy">
        <b-form-group label="Copy id" label-for="delete-copy-id">
          <b-form-select
            id="delete-copy-id"
            v-model="deleteCopyId"
            :options="copies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Delete copy</b-button>
        <p class="success-message" v-if="deleteCopySuccessMessage">
          {{ deleteCopySuccessMessage }}
        </p>
        <p class="error-message" v-if="deleteCopyErrorMessage">
          {{ deleteCopyErrorMessage }}
        </p>
      </b-form>
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
          <h2>Update item details</h2>
          <b-form @submit="updateMovie">
            <b-form-group label="Title" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.title"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Director" label-for="director">
              <b-form-input
                id="director"
                type="text"
                v-model="newItem.director"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Genre" label-for="genre">
              <b-form-input
                id="genre"
                type="text"
                v-model="newItem.genre"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Duration" label-for="length">
              <b-form-input
                id="length"
                type="number"
                v-model="newItem.length"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
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
      <b-button @click="addCopy">Add copy</b-button>
      <p class="success-message" v-if="addCopySuccessMessage">
        {{ addCopySuccessMessage }}
      </p>
      <p class="error-message" v-if="addCopyErrorMessage">
        {{ addCopyErrorMessage }}
      </p>
      <h2>Delete copy</h2>
      <b-form @submit="deleteCopy">
        <b-form-group label="Copy id" label-for="delete-copy-id">
          <b-form-select
            id="delete-copy-id"
            v-model="deleteCopyId"
            :options="copies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Delete copy</b-button>
        <p class="success-message" v-if="deleteCopySuccessMessage">
          {{ deleteCopySuccessMessage }}
        </p>
        <p class="error-message" v-if="deleteCopyErrorMessage">
          {{ deleteCopyErrorMessage }}
        </p>
      </b-form>
      <h2>Checkout copy</h2>
      <b-form @submit="checkOut">
        <b-form-group label="Copy id" label-for="checkout-copy-id">
          <b-form-select
            id="checkout-copy-id"
            v-model="checkOutCopyId"
            :options="availableCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-form-group label="Member id" label-for="checkout-member-id">
          <b-form-select
            id="checkout-member-id"
            v-model="checkOutMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Check out</b-button>
        <p class="success-message" v-if="checkOutSuccessMessage">
          {{ checkOutSuccessMessage }}
        </p>
        <p class="error-message" v-if="checkOutErrorMessage">
          {{ checkOutErrorMessage }}
        </p>
      </b-form>
      <h2>Return copy</h2>
      <b-form @submit="returnItem">
        <b-form-group label="Copy id" label-for="return-copy-id">
          <b-form-select
            id="return-copy-id"
            v-model="returnCopyId"
            :options="checkedOutCopies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Return</b-button>
        <p class="success-message" v-if="returnItemSuccessMessage">
          {{ returnItemSuccessMessage }}
        </p>
        <p class="error-message" v-if="returnItemErrorMessage">
          {{ returnItemErrorMessage }}
        </p>
      </b-form>
      <h2>Reserve item</h2>
      <b-form @submit="reserve">
        <b-form-group label="Member id" label-for="reserve-member-id">
          <b-form-select
            id="reserve-member-id"
            v-model="reserveMemberId"
            :options="members.map((member) => member.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Reserve</b-button>
        <p class="success-message" v-if="reserveSuccessMessage">
          {{ reserveSuccessMessage }}
        </p>
        <p class="error-message" v-if="reserveErrorMessage">
          {{ reserveErrorMessage }}
        </p>
      </b-form>
    </main>
    <main v-else-if="this.item.type === 'Newspaper'">
      <h1>{{ this.item.periodicalTitle }}</h1>
      <hr />
      <div class="main-container">
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
        <div class="item-actions-container">
          <h2>Update item details</h2>
          <b-form @submit="updateBook">
            <b-form-group label="Title of the periodical" label-for="title">
              <b-form-input
                id="title"
                type="text"
                v-model="newItem.periodicalTitle"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Date" label-for="date">
              <b-form-input
                id="date"
                type="date"
                v-model="newItem.date"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Frequency" label-for="frequency">
              <b-form-input
                id="frequency"
                type="text"
                v-model="newItem.frequency"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group label="Number" label-for="number">
              <b-form-input
                id="number"
                type="number"
                v-model="newItem.number"
                required
              ></b-form-input>
            </b-form-group>
            <b-button type="submit">Update</b-button>
            <p class="success-message" v-if="updateItemSuccessMessage">
              {{ updateItemSuccessMessage }}
            </p>
            <p class="error-message" v-if="updateItemErrorMessage">
              {{ updateItemErrorMessage }}
            </p>
          </b-form>
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
      <b-button @click="addCopy">Add copy</b-button>
      <p class="success-message" v-if="addCopySuccessMessage">
        {{ addCopySuccessMessage }}
      </p>
      <p class="error-message" v-if="addCopyErrorMessage">
        {{ addCopyErrorMessage }}
      </p>
      <h2>Delete copy</h2>
      <b-form @submit="deleteCopy">
        <b-form-group label="Copy id" label-for="delete-copy-id">
          <b-form-select
            id="delete-copy-id"
            v-model="deleteCopyId"
            :options="copies.map((copy) => copy.id)"
            required
          ></b-form-select>
        </b-form-group>
        <b-button type="submit">Delete copy</b-button>
        <p class="success-message" v-if="deleteCopySuccessMessage">
          {{ deleteCopySuccessMessage }}
        </p>
        <p class="error-message" v-if="deleteCopyErrorMessage">
          {{ deleteCopyErrorMessage }}
        </p>
      </b-form>
    </main>
  </body>
</template>

<script>
import Header from "./LibrarianHeader.vue";
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
  name: "LibrarianItem",
  data() {
    return {
      item: {},
      newItem: {},
      copies: [],
      copiesWithStatus: [],
      members: [],
      updateItemSuccessMessage: "",
      updateItemErrorMessage: "",
      addCopySuccessMessage: "",
      addCopyErrorMessage: "",
      deleteCopyId: "",
      deleteCopySuccessMessage: "",
      deleteCopyErrorMessage: "",
      checkOutCopyId: "",
      checkOutMemberId: "",
      checkOutSuccessMessage: "",
      checkOutErrorMessage: "",
      returnCopyId: "",
      returnItemSuccessMessage: "",
      returnItemErrorMessage: "",
      reserveMemberId: "",
      reserveSuccessMessage: "",
      reserveErrorMessage: ""
    };
  },
  components: {
    Header
  },
  created() {
    const promiseToFetchItem = this.fetchItemDetails(this.$route.params.itemId)
      .catch(error => {
        this.$router.replace({ name: "NotFound" });
      });

    const promisetoFetchCopies = this.fetchItemCopies(this.$route.params.itemId)
      .catch(error => {
        this.$router.replace({ name: "NotFound" });
      });

    Promise.all([promiseToFetchItem, promisetoFetchCopies]).then(
      this.fetchCopiesWithStatus
    );

    axios_instance
      .get(`/member/all`)
      .then(response => {
        this.members = response.data;
      })
      .catch(error => {
        this.$router.replace({ name: "NotFound" });
      });
  },
  beforeRouteUpdate(to, from, next) {
    const promiseToFetchItem = this.fetchItemDetails(to.params.itemId)
      .catch(error => {
        next({ name: "NotFound" });
      });
    const promiseToFetchCopies = this.fetchItemDetails(to.params.itemId)
      .catch(error => {
        next({ name: "NotFound" });
      });
    
    Promise.all([promiseToFetchItem, promiseToFetchCopies]).then(
      this.fetchCopiesWithStatus
    );
  },
  methods: {
    updateItem(event, endpoint) {
      event.preventDefault();
      axios_instance
        .put(
          `/${endpoint}/${this.item.id}`,
          {},
          { params: { ...this.newItem } }
        )
        .then(response => {
          this.updateItemSuccessMessage = "Item details updated successfully";
          this.updateItemErrorMessage = "";
          this.item = response.data;
        })
        .catch(error => {
          this.updateItemSuccessMessage = "";
          this.updateItemErrorMessage = "Could not update item details";
        });
    },
    updateBook(event) {
      this.updateItem(event, "bookInfo");
    },
    updateAlbum(event) {
      this.updateItem(event, "albumInfo");
    },
    updateArchive(event) {
      this.updateItem(event, "archiveInfo");
    },
    updateMovie(event) {
      this.updateItem(event, "movieInfo");
    },
    updateNewspaper(event) {
      this.updateItem(event, "newspaperInfo");
    },
    fetchItemDetails(itemId) {
      return axios_instance
      .get(`/libraryItemInfo/${itemId}`)
      .then(response => {
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
        return Promise.resolve();
      } else {
        return Promise.all(
          this.copies.map(copy =>
            axios_instance
              .get(`/reservableItem/${copy.id}/loan`)
              .then(response => {
                if (response.data.length === 0) {
                  return { ...copy, status: "Available" };
                } else {
                  return { ...copy, status: "Checked out" };
                }
              }).catch(error => {
                return { ...copy, status: "Could not fetch copy status"};
              })
          )
        ).then(responses => {
          this.copiesWithStatus = responses;
        });
      }
    },
    addCopy() {
      axios_instance
        .post(`/libraryItem/${this.item.id}`)
        .then(response => {
          this.addCopySuccessMessage = "Copy added successfully";
          this.addCopyErrorMessage = "";
          this.copies.push(response.data);
          this.fetchCopiesWithStatus();
        })
        .catch(error => {
          this.addCopySuccessMessage = "";
          this.addCopyErrorMessage = "Could not add copy";
        });
    },
    deleteCopy(event) {
      event.preventDefault();
      const endpoint = {
        Book: "book",
        Album: "album",
        Archive: "archive",
        Newspaper: "newspaper",
        Movie: "movie"
      }[this.item.type];
      axios_instance
        .delete(`/${endpoint}/${this.deleteCopyId}`)
        .then(response => {
          this.deleteCopySuccessMessage = "Copy deleted successfully";
          this.deleteCopyErrorMessage = "";
          this.copies = this.copies.filter(
            copy => copy.id !== this.deleteCopyId
          );
          this.fetchCopiesWithStatus();
        })
        .catch(error => {
          this.deleteCopySuccessMessage = "";
          this.deleteCopyErrorMessage = "Could not delete copy";
        });
    },
    checkOut(event) {
      event.preventDefault();
      axios_instance
        .post(
          `/reservableItem/${this.checkOutCopyId}/loan`,
          {},
          { params: { memberId: this.checkOutMemberId } }
        )
        .then(response => {
          this.checkOutSuccessMessage = `Copy checked out successfully. The loan is valid until ${response.data.returnDate}`;
          this.checkOutErrorMessage = "";
          this.checkOutCopyId = "";
          this.checkOutMemberId = "";
          this.fetchCopiesWithStatus();
        })
        .catch(error => {
          this.checkOutSuccessMessage = "";
          this.checkOutErrorMessage = "Could not check out copy";
        });
    },
    returnItem(event) {
      event.preventDefault();
      axios_instance
        .delete(`/reservableItem/${this.returnCopyId}/loan`)
        .then(_ => {
          this.returnItemSuccessMessage = "Copy returned successfully";
          this.returnItemErrorMessage = "";
          this.returnCopyId = "";
          this.fetchCopiesWithStatus();
        })
        .catch(error => {
          this.returnItemSuccessMessage = "";
          this.returnItemErrorMessage = "Could not return copy";
        });
    },
    reserve(event) {
      event.preventDefault();
      axios_instance
        .post(
          `/reservation`,
          {},
          {
            params: {
              memberId: this.reserveMemberId,
              reservableItemId: this.item.id
            }
          }
        )
        .then(_ => {
          this.reserveSuccessMessage = "Reservation made successfully";
          this.reserveErrorMessage = "";
          this.reserveMemberId = "";
        })
        .catch(error => {
          this.reserveSuccessMessage = "";
          this.reserveErrorMessage = "Could not make reservation";
        });
    }
  },
  computed: {
    availableCopies() {
      return this.copiesWithStatus.filter(copy => copy.status === "Available");
    },
    checkedOutCopies() {
      return this.copiesWithStatus.filter(
        copy => copy.status === "Checked out"
      );
    }
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin: 20px 0;
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
}
@media only screen and (max-width: 768px) {
  .main-container {
    flex-direction: column;
    align-items: stretch;
  }
}
.success-message {
  color: green;
}
.error-message {
  color: red;
}
table {
  margin-top: 20px;
}
main > button {
  margin: 20px 0;
}
main > h2 {
  margin-top: 20px;
}
</style>
