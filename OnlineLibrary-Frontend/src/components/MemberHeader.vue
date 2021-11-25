<template>
  <header>
    <nav>
      <ul>
        <li><router-link :to="{ name: 'MemberHome' }">Home</router-link></li>
        <li><router-link :to="{ name: 'MemberBrowse' }">Browse items</router-link></li>
        <li><router-link :to="{ name: 'MemberRooms' }">Browse rooms</router-link></li>
        <li><router-link :to="{ name: 'MemberLogout' }">Log out</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<style>
header nav ul {
  background-color: #000;
  height: 75px;
}
nav ul {
  display: flex;
}
nav ul li {
  font-size: 20px;
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
</style>