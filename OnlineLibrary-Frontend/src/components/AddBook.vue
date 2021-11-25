<template>
  <div class="form-container">
    <h2>Add a book</h2>
    <b-form @submit="createBook">
      <b-form-group id="input-group-1" label="Title" label-for="input-1">
        <b-form-input
          id="book-title"
          type="text"
          placeholder="Title"
          v-model="bookTitle"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group
        id="input-group-2"
        label="Number of pages"
        label-for="input-2"
      >
        <b-form-input
          id="book-number-of-pages"
          type="number"
          placeholder="Number of pages"
          v-model="bookNumberOfPages"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-3" label="Author" label-for="input-3">
        <b-form-input
          id="book-author"
          type="text"
          placeholder="Author"
          v-model="bookAuthor"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="ISBN" label-for="input-4">
        <b-form-input
          id="book-isbn"
          type="number"
          placeholder="ISBN"
          v-model="bookIsbn"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit">Add book</b-button>

      <!-- <label for="book-title">Title</label>
        <input id="book-title" type="text" placeholder="Title" v-model="bookTitle"/>
        <label for="book-number-of-pages">Number of pages</label>
        <input id="book-number-of-pages" type="number" placeholder="Number of pages" v-model="bookNumberOfPages"/>
        <label for="book-author">Author</label>
        <input id="book-author" type="text" placeholder="Author" v-model="bookAuthor"/>
        <label for="book-isbn">ISBN</label>
        <input id="book-isbn" type="number" placeholder="ISBN" v-model="bookIsbn"/>
        <button type="submit">Add book</button>-->
      <!-- <p class="error-message">-->
      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </b-form>
  </div>
  <!-- <table>
      <tr>
        <th>Title</th>
        <th>Number of pages</th>
        <th>Author</th>
        <th>ISBN</th>
      </tr>
      <tr>
          <td>
              <input type="text" placeholder="Title">
          </td>
          <td>
              <input type="text" placeholder="Number of pages">
          </td>
          <td>
              <input type="text" placeholder="Author">
          </td>
          <td>
              <input type="text" placeholder="ISBN">
          </td>
          <td>
              <button>Add book</button>
          </td>
      </tr>
    </table> -->
  <!-- <h2>Movies</h2>
    <table>
      <tr>
        <th>Genre</th>
        <th>Director</th>
        <th>Length</th>
      </tr>
      <tr>
        <td>
          <input type="text" placeholder="Genre" />
        </td>
        <td>
          <input type="text" placeholder="Director" />
        </td>
        <td>
          <input type="text" placeholder="Length" />
        </td>
        <td>
          <button>Add movie</button>
        </td>
      </tr>
    </table>
    <h2>Album</h2>
    <table>
      <tr>
        <th>Title</th>
        <th>Composer</th>
        <th>Genre</th>
      </tr>
      <tr>
        <td>
          <input type="text" placeholder="Title" />
        </td>
        <td>
          <input type="text" placeholder="Composer" />
        </td>
        <td>
          <input type="text" placeholder="Genre" />
        </td>
        <td>
          <button>Add album</button>
        </td>
      </tr>
    </table>
    <h2>Archive</h2>
    <table>
      <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Publication Date</th>
      </tr>
      <tr>
        <td>
          <input type="text" placeholder="Title" />
        </td>
        <td>
          <input type="text" placeholder="Description" />
        </td>
        <td>
          <input type="text" placeholder="Publication Date" />
        </td>
        <td>
          <button>Add archive</button>
        </td>
      </tr>
    </table>
    <h2>Newspaper</h2>
    <table>
      <tr>
        <th>Publication Date</th>
        <th>Frequency</th>
        <th>Number</th>
      </tr>
      <tr>
        <td>
          <input type="text" placeholder="Publication Date" />
        </td>
        <td>
          <input type="text" placeholder="Frequency" />
        </td>
        <td>
          <input type="text" placeholder="Number" />
        </td>
        <td>
          <button>Add newspaper</button>
        </td>
      </tr>
    </table> -->
</template>

<script>
import axios from "axios";
const config = require("../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
// Same for the frontend URL which may be used in some API responses...
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `http://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: "AddBook",
  data() {
    return {
      bookNumberOfPages: NaN,
      bookAuthor: "",
      bookIsbn: NaN,
      bookTitle: "",
      errorMessage: "",
    };
  },
  methods: {
    createBook(event) {
      event.preventDefault();
      console.log(
        this.bookNumberOfPages,
        this.bookAuthor,
        this.bookIsbn,
        this.bookTitle
      );
      axios_instance
        .post(
          `/bookInfo/${this.bookTitle}`,
          {},
          {
            params: {
              numberOfPage: Number.parseInt(this.bookNumberOfPages),
              author: this.bookAuthor,
              isbn: Number.parseInt(this.bookIsbn),
            },
          }
        )
        .then((response) => {
          this.errorMessage = "";
          const newBookId = response.data.id;
          this.$router.push({
            name: "LibrarianItem",
            params: { itemId: newBookId },
          });
        })
        .catch((error) => {
          console.error(error);
          this.errorMessage =
            "Could not create this item";
        });
    },
  },
};
</script>

<style scoped>
.error-message {
  color: red;
}
.form-container {
  margin: 20px;
}
</style>