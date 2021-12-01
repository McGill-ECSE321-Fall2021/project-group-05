<template>
  <body>
    <h1>Welcome to the online library system!</h1>
    <main>
      <h2>Log in to your online account:</h2>
      <hr/>
      <b-container>
        <b-row>
          <b-col>
            <h2>Member</h2>
            <b-form @submit="submitMemberForm">
              <b-row  class="b-row">
                <!-- Disallow spaces in username -->
                <b-input
                  class="b-input"
                  type="text"
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="memberUsername"
                />
              </b-row>
              <b-row  class="b-row">
                <!-- Disallow spaces in password -->
                <b-input
                  class="b-input"
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="memberPassword"
                />
              </b-row>
              <b-row>
                <b-button
                  class="b-button"
                  type="submit"
                  v-bind:disabled="
                    !memberUsername.trim() || !memberPassword.trim()
                  "
                >
                  Log in
                </b-button>
                <p>
                  Don't have an account? <router-link :to="{ name: 'Signup' }">Sign up</router-link>.
                </p>
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
            <b-form @submit="submitLibrarianForm">
              <b-row  class="b-row">
                <!-- Disallow spaces in username -->
                <b-input
                  class="b-input"
                  type="text"
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="librarianUsername"
                />
              </b-row>
              <b-row  class="b-row">
                <!-- Disallow spaces in password -->
                <b-input
                  class="b-input"
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="librarianPassword"
                />
              </b-row>
              <b-row>
                <b-button
                  class="b-button"
                  type="submit"
                  v-bind:disabled="
                    !librarianUsername.trim() || !librarianPassword.trim()
                  "
                >
                  Log in
                </b-button>
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
    </main>
  </body>
</template>

<script src="./login.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: bold;
  margin-top: 5vw;
  margin-bottom: 3vw;
}
h2 {
  font-size: 150%;
  padding-bottom: 1.5rem;
}
p {
  margin-left: 0.5rem;
  transform: translateY(0.5rem);
}
.b-row {
  margin-bottom: 0.5rem;
}
.b-input {
  width: 30vw;
  max-width: 27rem;
}
.b-button {
  margin-left: 0.5rem;
}
.errorMsg {
  color: red;
  text-align: left;
}
</style>
