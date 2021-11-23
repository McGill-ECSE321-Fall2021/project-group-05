import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello.vue'
import Rooms from '@/components/Rooms.vue'
import Login from '@/components/Login.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login/',
      name: 'Login',
      component: Login
    },
    {
      path: '/rooms/',
      name: 'Rooms',
      component: Rooms
    }
  ]
})
