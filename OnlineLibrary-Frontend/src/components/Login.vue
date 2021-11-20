<template>
  <div class="login">
    <h1>Welcome to the online library system!</h1>
    <h2>Log in to your online account:</h2>
    <b-container>
      <b-row>
        <b-col>
          <h2>Member</h2>
          <b-row>
            <!-- Disallow spaces in username -->
            <input
              type="text"
              @keydown.space.prevent
              placeholder="Username"
              v-model="memberUsername"
            />
          </b-row>
          <b-row>
            <!-- Disallow spaces in password -->
            <input
              type="text"
              @keydown.space.prevent
              placeholder="Password"
              v-model="memberPassword"
            />
          </b-row>
          <b-row>
            <button v-bind:disabled="!memberUsername.trim() || !memberPassword.trim()">Log in</button>
          </b-row>
        </b-col>
        <b-col>
          <h2>Employee</h2>
          <b-row>
            <!-- Disallow spaces in username -->
            <input
              type="text"
              @keydown.space.prevent
              placeholder="Username"
              v-model="librarianUsername"
            />
          </b-row>
          <b-row>
            <!-- Disallow spaces in password -->
            <input
              type="text"
              @keydown.space.prevent
              placeholder="Password"
              v-model="librarianPassword"
            />
          </b-row>
          <b-row>
            <button v-bind:disabled="!librarianUsername.trim() || !librarianPassword.trim()">Log in</button>
          </b-row>
        </b-col>
      </b-row>
    </b-container>
    <h2>
      Don't have an account? <router-link to="/signup">Sign up</router-link>
    </h2>
  </div>
</template>

<script src="./login.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: bold;
}
</style>
