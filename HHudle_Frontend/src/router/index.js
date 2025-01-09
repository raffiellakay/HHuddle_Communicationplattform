// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';


import AHomeLayout from '@/layouts/Admin/AHomeLayout.vue';
import UHomeLayout from '@/layouts/User/UHomeLayout.vue';
import UHomeView from '@/views/User/UHomeView.vue';
import BoardLayout from '@/layouts/User/BoardLayout.vue';
import CommonRoomsView from '@/views/User/CommonRoomsView.vue';
import BlackBoardView from '@/views/User/BlackBoardView.vue';
import PackageFinderView from '@/views/User/PackageFinderView.vue';
import SearchAndFindView from '@/views/User/SearchAndFindView.vue';
import AHomeView from '@/views/Admin/AHomeView.vue';
import LoginLayout from '@/layouts/LoginLayout.vue';

import AboutUsView from '@/views/AboutUsView.vue';
import ContactView from '@/views/ContactView.vue';
import AHouseLayout from '@/layouts/Admin/AHouseLayout.vue';
import HouseView from '@/views/Admin/HouseView.vue';


import { useAuthStore } from '@/stores/authStore';



const routes = [
  
  {
    path: '/user',
    name: 'Home',
    component: UHomeLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'home',
        name: 'UserHome',  //Achtung! Name muss unique sein
        component: UHomeView,
        meta: { requiresAuth: true },
      },
      {
        path: 'board',
        name: 'Board',
        component: BoardLayout,
        meta: { requiresAuth: true  },
        children: [
          {
            path: 'commonrooms',
            component: CommonRoomsView,
            meta: { requiresAuth: true }, 
          },
          {
            path: 'blackboard',
            component: BlackBoardView,
            meta: { requiresAuth: true  },
          },
          {
            path: 'packagefinder',
            component: PackageFinderView,
            meta: { requiresAuth: true },
          },
          {
            path:'search&find',
            component: SearchAndFindView,
            meta: { requiresAuth: true },
          }]
      }]
    },
  {
    path: '/admin',
    name: 'admin',
    component: AHomeLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: AHomeView,
        meta: { requiresAuth: true  },
      }, 
    
      {
        path: 'house/:houseId/adminposts',
        name: 'House',
        component: HouseView,
        props: true,
        meta: { requiresAuth: true  },
      }
    ]
  },

  {
    path: '/',
    name: 'Login',
    component: LoginLayout,
    meta: { requiresAuth: false },
    children: [
      {
        path: 'aboutUs',
        name: 'AboutUs',
        component: AboutUsView,
        meta: { requiresAuth: false },
      },
      {
        path: 'contact',
        name: 'Contact',
        component: ContactView,
        meta: { requiresAuth: false },
      }
    ]
  },


  ]
      
  



  // Weitere Routen können hier hinzugefügt werden


const router = createRouter({
  history: createWebHistory(),
  routes,
});



router.beforeEach(async (to, from, next) => {
  await useAuthStore().initialize();
  //secures Websites from not logged in ppl
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const loggedIn = useAuthStore().user;
    if (requiresAuth && !loggedIn) {
       next('/');
      } 
      else {
        next();
      }
  // secures paths from ppl with wrong role
  // hier fehlt noch was 
    })

export default router;