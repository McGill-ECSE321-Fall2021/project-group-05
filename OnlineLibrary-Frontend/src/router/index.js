import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home.vue";
import Browse from "@/components/Browse.vue";
import Item from "@/components/Item.vue";
import Rooms from "@/components/Rooms.vue";
import Room from "@/components/Room.vue";
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
      path: "/browse/",
      name: "Browse",
      component: Browse
    },
    {
      path: "/item/:itemId/",
      name: "Item",
      component: Item
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
      path: "/rooms/:roomId/",
      name: "Room",
      component: Room
    },
    {
      path: "*",
      redirect: "/404/"
    }
  ]
});
