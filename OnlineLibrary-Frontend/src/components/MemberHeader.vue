<template>
  <header>
    <nav>
      <div class="wrapper">
        <div class="nav-left">
          <ul>
            <li>
              <router-link :to="{ name: 'MemberHome' }">Home</router-link>
            </li>
            <li>
              <router-link :to="{ name: 'MemberBrowse' }"
                >Browse items</router-link
              >
            </li>
            <li>
              <router-link :to="{ name: 'MemberRooms' }"
                >Browse rooms</router-link
              >
            </li>
            <li>
              <router-link :to="{ name: 'MemberUpdateInfo' }">
                Update personal info
              </router-link>
            </li>
            <li>
              <router-link
                :to="{ name: 'Scheduler', params: {variant: 'RoomBooking'}}"
              >
                Room Booking
              </router-link>
            </li>
            <li>
              <router-link
                :to="{ name: 'Scheduler', params: {variant: 'Schedule'}}"
              >
                Schedule
              </router-link>
            </li>
          </ul>
        </div>
        <div class="nav-right">
          <ul>
            <li>
              <router-link :to="{ name: 'MemberLogout' }">Log out</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<style>
.wrapper {
  background-color: #000;
  display: flex;
  justify-content: space-between;
  width: 100%;
  min-height: 5rem;
  padding-top: 1rem;
  padding-bottom: 0rem;
}
nav ul {
  display: flex;
}
nav ul li {
  font-size: 1.3em;
  text-align: center;
  padding: 20px 50px;
  justify-content: center;
  list-style-type: none;
  align-self: center;
}
nav ul li a {
  color: #f2ebeb;
  text-decoration: none;
}
nav ul li a:hover {
  color: #f2ebeb;
  text-decoration-style: dotted;
}
.nav-left {
  float: left;
}
.nav-left ul li {
  float: left;
}
.nav-right {
  float: right;
}
.nav-right ul li {
  float: right;
}
</style>
