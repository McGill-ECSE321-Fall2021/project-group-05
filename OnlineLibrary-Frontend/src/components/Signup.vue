<template>
  <body>
    <h1>Welcome to the online library system!</h1>
    <main>
      <h2>Create your new account:</h2>
      <div class="checkbox">
        <label>
          <input type="checkbox" v-model="hasInPersonAccount" />
          I already have an in-person account
        </label>
      </div>
      <b-container>
        <b-row>
          <!-- Existing in-person member -->
          <b-col v-if="hasInPersonAccount">
            <b-form @submit="submitInPersonMemberForm">
              <b-row>
                <!-- Member ID -->
                <b-input
                  type="number"
                  required
                  min="1"
                  placeholder="Library ID"
                  v-model="memberId"
                />
              </b-row>
              <b-row>
                <!-- Email address -->
                <b-input
                  type="email"
                  required
                  placeholder="Email address"
                  v-model="emailAddress"
                />
              </b-row>
              <b-row>
                <!-- New username -->
                <!-- Disallow spaces -->
                <b-input
                  type="text"
                  required
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="username"
                />
              </b-row>
              <b-row>
                <!-- New password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  required
                  pattern=".{8,}"
                  title="- At least 8 characters in length"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="password"
                />
              </b-row>
              <b-row>
                <!-- Confirm new password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  required
                  v-bind:pattern="'^' + password + '$'"
                  title="- Matches first password"
                  @keydown.space.prevent
                  placeholder="Confirm password"
                  v-model="passwordConfirmation"
                />
              </b-row>
              <b-row>
                <b-button
                  type="submit"
                >
                  Sign up
                </b-button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="errorMsg" class="errorMsg">
                Error: {{ errorMsg }}
              </span>
            </b-row>
          </b-col>
          <!-- New member -->
          <b-col v-if="!hasInPersonAccount">
            <b-form @submit="submitNewMemberForm">
              <b-row>
                <!-- Full name -->
                <b-input
                  type="text"
                  required
                  placeholder="Full name"
                  v-model="fullName"
                />
              </b-row>
              <b-row>
                <!-- Physical address -->
                <b-input
                  type="text"
                  required
                  placeholder="Physical address"
                  v-model="physicalAddress"
                />
              </b-row>
              <b-row>
                <!-- Email address -->
                <b-input
                  type="email"
                  required
                  placeholder="Email address"
                  v-model="emailAddress"
                />
              </b-row>
              <b-row>
                <!-- Username -->
                <!-- Disallow spaces -->
                <b-input
                  type="text"
                  required
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="username"
                />
              </b-row>
              <b-row>
                <!-- Password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  required
                  pattern=".{8,}"
                  title="- At least 8 characters in length"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="password"
                />
              </b-row>
              <b-row>
                <!-- Confirm password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  required
                  v-bind:pattern="'^' + password + '$'"
                  title="- Matches first password"
                  @keydown.space.prevent
                  placeholder="Confirm password"
                  v-model="passwordConfirmation"
                />
              </b-row>
              <b-row>
                <b-button
                  type="submit"
                >
                  Sign up
                </b-button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="errorMsg" class="errorMsg">
                Error: {{ errorMsg }}
              </span>
            </b-row>
          </b-col>
        </b-row>
      </b-container>
      <p>
        Already have an online account?
        <router-link :to="{ name: 'Login' }">Log in</router-link>.
      </p>
    </main>
  </body>
</template>

<script src="./signup.js">
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
