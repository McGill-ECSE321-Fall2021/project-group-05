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
import Scheduler from "@/components/Scheduler/Scheduler.vue";
import NotFound from "@/components/NotFound.vue";
import LibrarianRooms from "@/components/LibrarianRooms.vue";
import LibrarianRoom from "@/components/LibrarianRoom.vue";
import LibrarianUpdateLoginInfo from "@/components/LibrarianUpdateLoginInfo.vue";
import MemberUpdateInfo from "@/components/MemberUpdateInfo.vue";
import Signup from "@/components/Signup.vue";
import Inactive from "@/components/Inactive.vue";
import Blacklisted from "@/components/Blacklisted.vue";
import LibrarianManageMembers from "@/components/LibrarianManageMembers.vue";
import LibrarianManageLibrarians from "@/components/LibrarianManageLibrarians.vue";

Vue.use(Router);

function requireMember(from, to, next) {
  // Not logged in as member
  if (!sessionStorage.getItem("loggedInMember")) {
    next({ name: 'Login' });
  }
  // Continue to requested page
  else {
    next();
  }
}
function requireLibrarian(from, to, next) {
  // Not logged in as librarian
  if (!sessionStorage.getItem("loggedInLibrarian")) {
    next({ name: 'Login' });
  }
  // Continue to requested page
  else {
    next();
  }
}
function requireHeadLibrarian(from, to, next) {
  // Not logged in as librarian
  const librarianStr = sessionStorage.getItem("loggedInLibrarian");
  if (!librarianStr) {
    next({ name: 'Login' });
  }
  else {
    // Not head
    const librarianObj = JSON.parse(librarianStr).librarian;
    if (!librarianObj.head) {
      next({ name: 'Login' });
    }
    // Continue to requested page
    else {
      next();
    }
  }
}
function requireLoggedIn(from, to, next) {
  // Not logged in at all
  if (!sessionStorage.getItem("loggedInMember") &&
    !sessionStorage.getItem("loggedInLibrarian")) {
    next({ name: 'Login' });
  } else {
    next();
  }
}

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
      component: MemberHome,
      beforeEnter: requireMember
    },
    {
      path: "/librarian/home",
      name: "LibrarianHome",
      component: LibrarianHome,
      beforeEnter: requireLibrarian
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
      component: MemberBrowse,
      beforeEnter: requireMember
    },
    {
      path: "/librarian/browse",
      name: "LibrarianBrowse",
      component: LibrarianBrowse,
      beforeEnter: requireLibrarian
    },
    {
      path: "/member/item/:itemId/",
      name: "MemberItem",
      component: MemberItem,
      beforeEnter: requireMember
    },
    {
      path: "/librarian/item/:itemId/",
      name: "LibrarianItem",
      component: LibrarianItem,
      beforeEnter: requireLibrarian
    },
    {
      path: "/404",
      name: "NotFound",
      component: NotFound
    },
    {
      path: "/member/rooms",
      name: "MemberRooms",
      component: MemberRooms,
      beforeEnter: requireMember
    },
    {
      path: "/member/rooms/:roomId",
      name: "MemberRoom",
      component: MemberRoom,
      beforeEnter: requireMember
    },
    {
      path: "/librarian/rooms",
      name: "LibrarianRooms",
      component: LibrarianRooms,
      beforeEnter: requireLibrarian
    },
    {
      path: "/librarian/rooms/:roomId",
      name: "LibrarianRoom",
      component: LibrarianRoom,
      beforeEnter: requireLibrarian
    },
    {
      path: "/librarian/manageMembers",
      name: "LibrarianManageMembers",
      component: LibrarianManageMembers,
      beforeEnter: requireLibrarian
    },
    {
      path: "/librarian/manageLibrarians",
      name: "LibrarianManageLibrarians",
      component: LibrarianManageLibrarians,
      beforeEnter: requireHeadLibrarian
    },
    {
      path: "/librarian/librarianUpdateLoginInfo/",
      name: "LibrarianUpdateLoginInfo",
      component: LibrarianUpdateLoginInfo
    },
    {
      path: "/member/memberUpdateInfo",
      name: "MemberUpdateInfo",
      component: MemberUpdateInfo
    },
    {
      path: "/scheduler/:variant",
      name: "Scheduler",
      component: Scheduler,
      props: true,
      beforeEnter: requireLoggedIn
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
});
