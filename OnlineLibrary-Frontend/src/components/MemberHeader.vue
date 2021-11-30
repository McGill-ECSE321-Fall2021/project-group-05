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
  height: 75px;
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
  color: #fff;
  text-decoration: none;
}
nav ul li a:hover {
  color: #fff;
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
