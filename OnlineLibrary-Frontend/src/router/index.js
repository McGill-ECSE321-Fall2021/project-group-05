import Vue from "vue";
import Router from "vue-router";
import MemberHome from "@/components/MemberHome.vue";
import LibrarianHome from "@/components/LibrarianHome.vue";
import Login from "@/components/Login.vue";
import MemberLogout from "@/components/MemberLogout.vue";
import LibrarianLogout from "@/components/LibrarianLogout.vue";
import MemberRooms from "@/components/MemberRooms.vue";
import MemberRoom from "@/components/MemberRoom.vue";
import MemberBrowse from "@/components/MemberBrowse.vue";
import LibrarianBrowse from "@/components/LibrarianBrowse.vue";
import MemberItem from "@/components/MemberItem.vue";
import LibrarianItem from "@/components/LibrarianItem.vue";
import NotFound from "@/components/NotFound.vue";
import LibrarianRooms from "@/components/LibrarianRooms.vue";
import LibrarianRoom from "@/components/LibrarianRoom.vue";
import Signup from "@/components/Signup.vue";
import Inactive from "@/components/Inactive.vue";
import Blacklisted from "@/components/Blacklisted.vue";

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: "/",
      redirect: "/login"
    },
    {
      path: "/signup",
      name: "Signup",
      component: Signup
    },
    {
      path: "/inactive",
      name: "Inactive",
      component: Inactive
    },
    {
      path: "/blacklisted",
      name: "Blacklisted",
      component: Blacklisted
    },
    {
      path: "/member/home",
      name: "MemberHome",
      component: MemberHome
    },
    {
      path: "/librarian/home",
      name: "LibrarianHome",
      component: LibrarianHome
    },
    {
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path: "/member/logout",
      name: "MemberLogout",
      component: MemberLogout
    },
    {
      path: "/librarian/logout",
      name: "LibrarianLogout",
      component: LibrarianLogout
    },
    {
      path: "/member/browse/",
      name: "MemberBrowse",
      component: MemberBrowse
    },
    {
      path: "/librarian/browse",
      name: "LibrarianBrowse",
      component: LibrarianBrowse
    },
    {
      path: "/member/item/:itemId/",
      name: "MemberItem",
      component: MemberItem
    },
    {
      path: "/librarian/item/:itemId/",
      name: "LibrarianItem",
      component: LibrarianItem
    },
    {
      path: "/404",
      name: "NotFound",
      component: NotFound
    },
    {
      path: "/member/rooms",
      name: "MemberRooms",
      component: MemberRooms
    },
    {
      path: "/member/rooms/:roomId",
      name: "MemberRoom",
      component: MemberRoom
    },
    {
      path: "/librarian/rooms",
      name: "LibrarianRooms",
      component: LibrarianRooms
    },
    {
      path: "/librarian/rooms/:roomId",
      name: "LibrarianRoom",
      component: LibrarianRoom
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
});
