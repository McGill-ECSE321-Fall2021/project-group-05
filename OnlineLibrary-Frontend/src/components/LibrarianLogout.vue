<template>
  <div class="logout">
    <h1>Logging you out...</h1>
    <p>
      You are now logged out. Redirecting you to
      <router-link :to="{ name: 'Login' }">the login page</router-link>...
    </p>
  </div>
</template>

<script>
import Login from "@/components/Login.vue";

export default {
  name: "LibrarianLogout",
  created() {
    sessionStorage.removeItem("loggedInLibrarian");
    this.$router.replace(Login);
  }
};
</script>
