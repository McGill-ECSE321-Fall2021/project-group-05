<template>
  <body>
    <main>
      <h1>404 - Page Not Found</h1>
      <h2>
        Oops! We couldn't find the page you were looking for. 😢 Go back
        <!-- Assume Login component will redirect to the appropriate home page -->
        <router-link :to="{ name: 'Login' }">home</router-link>?
      </h2>
    </main>
  </body>
</template>
