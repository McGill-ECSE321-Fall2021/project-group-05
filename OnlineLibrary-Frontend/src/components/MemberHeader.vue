<template>
  <header>
    <b-navbar toggleable="md" variant="dark" type="dark">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'MemberHome' }">Home</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'MemberBrowse' }">Browse items</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'MemberRooms' }">Browse rooms</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav>
          <b-nav-item :to="{ name: 'MemberUpdateInfo' }">
            Update personal info
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
          <b-nav-item :to="{ name: 'MemberLogout' }">Log out</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </header>
</template>

<style>
/* Hide headers for pages that are incompatible with mobile */
@media only screen and (max-width: 768px) {
  .hidden-on-mobile {
    display: none;
  }
}
</style>
