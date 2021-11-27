<template>
  <body>
    <Header />
    <main>
      <h1>Welcome to the online library system!</h1>
      <div class="item-picture-container">
        <img class="blue" src="../assets/book.svg" alt="Book cover" />
        <img class="red" src="../assets/album.svg" alt="Album cover" />
        <img class="yellow" src="../assets/archive.svg" alt="Archive box" />
        <img class="green" src="../assets/movie.svg" alt="Movie icon" />
        <img class="purple" src="../assets/newspaper.svg" alt="Newspaper" />
      </div>
      <h4>My reservation</h4>
      <table v-if="reservations.length !== 0">
        <tr>
          <th>Item ID</th>
          <th>Item title</th>
          <th>Item details</th>
        </tr>
        <tr v-for="reservation in reservations" :key="reservation.id">
          <td>
            {{ reservation.reservableItemInfo.id }}
          </td>
          <td>
            {{ reservation.reservableItemInfo.title }}
          </td>
          <td>
            <router-link
              :to="{
                name: 'MemberItem',
                params: { itemId: reservation.reservableItemInfo.id },
              }"
            >
              View item details
            </router-link>
          </td>
        </tr>
      </table>
      <p v-else-if="errorMessageReservation.length === 0">You don't have any reservation</p>
      <p class="error-message" v-if="errorMessageReservation"> {{errorMessageReservation}} </p>
      <h4>My loans</h4>
      <table v-if="loans.length !== 0">
        <tr>
          <th>Item ID</th>
          <th>Return date</th>
          <th>Number of renewals</th>
          <th>Item title</th>
          <th>Item details</th>
        </tr>
        <tr v-for="loan in loans" :key="loan.id">
          <td>
            {{ loan.reservableItem.id }}
          </td>
          <td>
            {{ loan.returnDate }}
          </td>
          <td>
            {{ loan.numberOfRenewals }}
          </td>

          <td v-if="loan.reservableItem.bookInfo != null">
            {{ loan.reservableItem.bookInfo.title }}
          </td>
          <td v-else-if="loan.reservableItem.movieInfo != null">
            {{ loan.reservableItem.movieInfo.title }}
          </td>
          <td v-else-if="loan.reservableItem.albumInfo != null">
            {{ loan.reservableItem.albumInfo.title }}
          </td>
          <td v-else>Could not find item title</td>

          <td v-if="loan.reservableItem.bookInfo != null">
            <router-link
              :to="{
                name: 'MemberItem',
                params: { itemId: loan.reservableItem.bookInfo.id },
              }"
            >
              View item details
            </router-link>
          </td>
          <td v-else-if="loan.reservableItem.movieInfo != null">
            <router-link
              :to="{
                name: 'MemberItem',
                params: { itemId: loan.reservableItem.movieInfo.id },
              }"
            >
              View item details
            </router-link>
          </td>
          <td v-else-if="loan.reservableItem.albumInfo != null">
            <router-link
              :to="{
                name: 'MemberItem',
                params: { itemId: loan.reservableItem.albumInfo.id },
              }"
            >
              View item details
            </router-link>
          </td>
          <td v-else>Could not find item details</td>
        </tr>
      </table>
      <p v-else-if="errorMessageLoans.length ===0">You don't have any reservation</p>
      <p class="error-message" v-if="errorMessageLoans"> {{errorMessageLoans}} </p>
      <h4>My room bookings</h4>
      <!--Todo: make this div conditional on the presence of Room bookings else show: no room booked-->
      <table v-if="roomBookings.length !==0">
        <tr>
          <th>Room</th>
          <th>Capacity</th>
          <th>Date</th>
          <th>Start time</th>
          <th>End time</th>
          <th>Room details</th>
        </tr>
        <tr v-for="roomBooking in roomBookings" :key="roomBooking.id">
          <td>{{ roomBooking.room.name }}</td>
          <td>{{ roomBooking.room.capacity }}</td>
          <td>{{ roomBooking.date }}</td>
          <td>{{ roomBooking.startTime }}</td>
          <td>{{ roomBooking.endTime }}</td>
          <router-link
            :to="{
              name: 'MemberRoom',
              params: { roomId: roomBooking.room.id },
            }"
          >
             View room details
          </router-link>
        </tr>
      </table>
      <p v-else-if="errorMessageRoomBookings.length ===0">You don't have any room bookings</p>
      <p class="error-message" v-if="errorMessageRoomBookings">
        {{ errorMessageRoomBookings }}
      </p>
    </main>
  </body>
</template>

<script>
import Header from "./MemberHeader.vue";
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

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  components: { Header },
  name: "MemberHome",
  data() {
    return {
      reservations: [],
      loans: [],
      roomBookings: [],
      errorMessageReservation: "",
      errorMessageLoans: "",
      errorMessageRoomBookings: ""
    };
  },
  created: function () {
    const storedCredentials = window.sessionStorage.getItem("loggedInMember");
    if (storedCredentials == null) {
      this.$router.push({ name: "Login" });
    }
    const loggedInMember = JSON.parse(storedCredentials);
    axios_instance
      .get(`/member/${loggedInMember.member.id}/reservation`)
      .then((response) => {
        this.reservations = response.data;
        this.errorMessageReservation = "";
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
        this.errorMessageReservation ="Oops! 🙁 Something bad happened on our side while trying to load your reservation. Try again later";
      });
    axios_instance
      .get(`/member/${loggedInMember.member.id}/loans/`)
      .then((response) => {
        this.loans = response.data;
        this.errorMessageLoans = "";
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
        this.errorMessageLoans ="Oops! 🙁 Something bad happened on our side while trying to load your loans. Try again later";
      });
    axios_instance
      .get(`/member/${loggedInMember.member.id}/roomBookings`)
      .then((response) => {
        this.roomBookings = response.data;
        this.errorMessageRoomBookings = "";
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
        this.errorMessageRoomBookings ="Oops! 🙁 Something bad happened on our side while trying to load your room bookings. Try again later";
      });
  },
};
</script>

<style scoped>
h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}
.item-picture-container {
  display: flex;
  justify-content: space-evenly;
}

.error-message {
  margin: 20px;
  text-align: center;
  color: red;
}
</style>
