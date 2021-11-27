<template>
  <body>
    <h1>Welcome to the online library system!</h1>
    <main>
      <h2>Create your new account:</h2>
      <b-container>
        <b-row>
          <b-col>
            <h2>Existing in-person member</h2>
            <b-form @submit="submitInPersonMemberForm">
              <b-row>
                <!-- Member ID -->
                <b-input
                  type="number"
                  placeholder="Library ID"
                  v-model="inPersonMemberId"
                />
              </b-row>
              <b-row>
                <!-- Email address -->
                <!-- Disallow spaces -->
                <b-input
                  type="email"
                  @keydown.space.prevent
                  placeholder="Email address"
                  v-model="inPersonMemberEmailAddress"
                />
              </b-row>
              <b-row>
                <!-- New username -->
                <!-- Disallow spaces -->
                <b-input
                  type="text"
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="inPersonMemberUsername"
                />
              </b-row>
              <b-row>
                <!-- New password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="inPersonMemberPassword"
                />
              </b-row>
              <b-row>
                <!-- Confirm new password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  @keydown.space.prevent
                  placeholder="Confirm password"
                  v-model="inPersonMemberPasswordConfirmation"
                />
              </b-row>
              <b-row>
                <b-button type="submit" v-bind:disabled="!isInPersonMemberInfoValid()">
                  Sign up
                </b-button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="inPersonMemberErrorMsg" class="errorMsg">
                Error: {{ inPersonMemberErrorMsg }}
              </span>
            </b-row>
          </b-col>
          <b-col>
            <h2>New member</h2>
            <b-form @submit="submitNewMemberForm">
              <b-row>
                <!-- Full name -->
                <b-input
                  type="text"
                  placeholder="Full name"
                  v-model="newMemberFullName"
                />
              </b-row>
              <b-row>
                <!-- Physical address -->
                <b-input
                  type="text"
                  placeholder="Physical address"
                  v-model="newMemberPhysicalAddress"
                />
              </b-row>
              <b-row>
                <!-- Email address -->
                <b-input
                  type="email"
                  placeholder="Email address"
                  v-model="newMemberEmailAddress"
                />
              </b-row>
              <b-row>
                <!-- Username -->
                <!-- Disallow spaces -->
                <b-input
                  type="text"
                  @keydown.space.prevent
                  placeholder="Username"
                  v-model="newMemberUsername"
                />
              </b-row>
              <b-row>
                <!-- Password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  @keydown.space.prevent
                  placeholder="Password"
                  v-model="newMemberPassword"
                />
              </b-row>
              <b-row>
                <!-- Confirm password -->
                <!-- Disallow spaces -->
                <b-input
                  type="password"
                  @keydown.space.prevent
                  placeholder="Confirm password"
                  v-model="newMemberPasswordConfirmation"
                />
              </b-row>
              <b-row>
                <b-button type="submit" v-bind:disabled="!isNewMemberInfoValid()">
                  Sign up
                </b-button>
              </b-row>
            </b-form>
            <b-row>
              <span v-if="newMemberErrorMsg" class="errorMsg">
                Error: {{ newMemberErrorMsg }}
              </span>
            </b-row>
          </b-col>
        </b-row>
      </b-container>
      <p>
        Already have an online account?
        <router-link :to="{ name: 'Login' }">Log in.</router-link>
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
