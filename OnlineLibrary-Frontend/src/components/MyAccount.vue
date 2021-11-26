<template>
  <body>
    <Header />
    <main>
      <h4>My Reservation</h4>
      <!--Todo: make this table conditional on the presence of Reservation else show: no reservation-->
      <table>
        <tr>
          <td>Hello</td>
          <td>World</td>
        </tr>
        <tr v-for="reservation in reservations" :key="reservation.id">
          <td>
            {{ reservation.reservableItemInfo }}
          </td>
          <td>
            {{ reservation.date }}
          </td>
        </tr>
      </table>

      <h4>My Loans</h4>
      <!--Todo: make this table conditional on the presence of Loans else show: no loans-->
      <table>
        <tr>
          <td>Loans Goes here</td>
        </tr>
      </table>

      <h4>My Room Bookings</h4>
      <!--Todo: make this div conditional on the presence of Room bookings else show: no room booked-->
      <table>
        <tr>
          <th>Room</th>
          <th>Capacity</th>
          <th>Date</th>
          <th>Start time</th>
          <th>End time</th>
        </tr>
        <tr>
          <td>Room Bookings goes here</td>
        </tr>
      </table>
    </main>
  </body>
</template>

<script>
import Header from "./Header.vue";
import axios from "axios";
const config = require("../../config");

const frontendUrl = "http://" + config.build.host + ":" + config.build.port;
const backendUrl =
  "http://" + config.build.backendHost + ":" + config.build.backendPort;

const axios_instance = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

function ReservationDto (date, member, id, reservableItemInfo) {
  this.date = date
  this.member = member
  this.id = id
  this.reservableItemInfo = reservableItemInfo
}

export default {
  name: "MyAccount",
  data() {
    return {
      reservations: [],
      loans: [],
      roomBookings: []
    };
  },
  created: function () {
        const r1 = new ReservationDto('2021-11-11', 'aMember', '218', 'reservableItemInfo');
        const r2 = new ReservationDto('2020-11-11 ', 'member2', '318', 'reserbalbeItemInfo2');
        this.reservations = [r1,r2];
  },
  
  components: {
    Header
  },
};
</script>
