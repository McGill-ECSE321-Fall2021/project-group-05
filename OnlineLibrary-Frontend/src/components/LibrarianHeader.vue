<template>
  <header>
    <b-navbar toggleable="md" variant="dark" type="dark">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'LibrarianHome' }">Home</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'LibrarianBrowse' }">
            Browse items
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'LibrarianRooms' }">
            Browse rooms
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="hidden-on-mobile">
          <b-nav-item :to="{ name: 'LibrarianManageMembers' }">
            Manage members
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="hidden-on-mobile">
          <b-nav-item
            v-if="isHeadLibrarian"
            :to="{ name: 'LibrarianManageLibrarians' }"
          >
            Manage employees
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'LibrarianUpdateLoginInfo' }">
            Update Login info
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="hidden-on-mobile">
          <b-nav-item
            :to="{ name: 'Scheduler', params: { variant: 'RoomBooking' } }"
          >
            Room Booking
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="hidden-on-mobile">
          <b-nav-item
            :to="{ name: 'Scheduler', params: { variant: 'Schedule' } }"
          >
            Schedule
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item :to="{ name: 'LibrarianLogout' }">Log out</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </header>
</template>

<script>
export default {
  name: "LibrarianHeader",
  created() {
    const librarian = JSON.parse(sessionStorage.getItem("loggedInLibrarian"))
      .librarian;
    this.isHeadLibrarian = librarian.head;
  },
  data() {
    return {
      isHeadLibrarian: false
    };
  }
};
</script>

<style>
/* Hide headers for pages that are incompatible with mobile */
@media only screen and (max-width: 768px) {
  .hidden-on-mobile {
    display: none !important;
  }
}
</style>
