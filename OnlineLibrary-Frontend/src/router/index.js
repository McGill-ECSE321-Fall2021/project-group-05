import Vue from "vue";
import Router from "vue-router";
import MemberHome from "@/components/MemberHome.vue";
import LibrarianHome from "@/components/LibrarianHome.vue";
import Rooms from "@/components/Rooms.vue";
import Login from "@/components/Login.vue";
import MemberLogout from "@/components/MemberLogout.vue";
import LibrarianLogout from "@/components/LibrarianLogout.vue";
import Rooms from "@/components/Rooms.vue";
import Room from "@/components/Room.vue";
import NotFound from "@/components/NotFound.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      redirect: "/login"
    },
    {
      path: "/home/member",
      name: "MemberHome",
      component: MemberHome
    },
    {
      path: "/home/librarian",
      name: "LibrarianHome",
      component: LibrarianHome
    },
    {
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path: "/logout/member",
      name: "MemberLogout",
      component: MemberLogout
    },
    {
      path: "/logout/librarian",
      name: "LibrarianLogout",
      component: LibrarianLogout
    },
    {
      path: "/404",
      name: "NotFound",
      component: NotFound
    },
    {
      path: "/rooms",
      name: "Rooms",
      component: Rooms
    },
    {
      path: "/rooms/:roomId",
      name: "Room",
      component: Room
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
});
