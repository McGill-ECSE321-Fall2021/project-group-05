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
export default {
  name: "MemberLogout",
  created() {
    sessionStorage.removeItem("loggedInMember");
    window.location.replace("/#/login/");
  }
};
</script>