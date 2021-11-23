import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home.vue";
import Rooms from "@/components/Rooms.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home
    },
    {
      path: '/rooms/',
      name: 'Rooms',
      component: Rooms
    }
  ]
});
