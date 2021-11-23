import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home.vue";
import Rooms from "@/components/Rooms.vue";
import NotFound from "@/components/NotFound.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home
    },
    {
      path: "/404/",
      name: "NotFound",
      component: NotFound
    },
    {
      path: "/rooms/",
      name: "Rooms",
      component: Rooms
    },
    {
      path: "*",
      redirect: "/404/"
    }
  ]
});
