<template>
  <header>
    <nav>
      <ul>
        <li><router-link :to="{ name: 'Home' }">Home</router-link></li>
        <li>
          <router-link :to="{ name: 'Browse' }">Browse items</router-link>
        </li>
        <li><router-link :to="{ name: 'Rooms' }">Browse rooms</router-link></li>
        <li><a>Log out</a></li>
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
</style>