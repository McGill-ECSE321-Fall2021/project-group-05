<template>
  <body>
    <h1>Welcome to the online library system!</h1>
    <main>
      <h2>Log in to your online account:</h2>
      <b-container>
        <b-row>
          <b-col>
            <h2>Member</h2>
            <b-form>
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
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="memberPassword"
                />
              </b-row>
              <b-row>
                <button
                  v-bind:disabled="
                    !memberUsername.trim() || !memberPassword.trim()
                  "
                  v-on:click="doLogInMember(memberUsername, memberPassword)"
                >
                  Log in
                </button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="memberErrorMsg" class="errorMsg">
                Error: {{ memberErrorMsg }}
              </span>
            </b-row>
          </b-col>
          <b-col>
            <h2>Employee</h2>
            <b-form>
              <b-row>
                <!-- Disallow spaces in username -->
                <input
                  type="username"
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="librarianUsername"
                />
              </b-row>
              <b-row>
                <!-- Disallow spaces in password -->
                <input
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="librarianPassword"
                />
              </b-row>
              <b-row>
                <button
                  v-bind:disabled="
                    !librarianUsername.trim() || !librarianPassword.trim()
                  "
                  v-on:click="
                    doLogInLibrarian(librarianUsername, librarianPassword)
                  "
                >
                  Log in
                </button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="librarianErrorMsg" class="errorMsg">
                Error: {{ librarianErrorMsg }}
              </span>
            </b-row>
          </b-col>
        </b-row>
      </b-container>
      <p>
        Don't have an account? <router-link to="/signup">Sign up</router-link>
      </p>
    </main>
  </body>
</template>

<script src="./login.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: bold;
}
h2 {
  font-size: 150%;
}
.errorMsg {
  color: red;
  text-align: left;
}
</style>
