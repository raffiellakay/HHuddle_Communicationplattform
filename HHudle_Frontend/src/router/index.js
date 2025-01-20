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
import AllHousesView from '@/views/Admin/AllHousesView.vue';
import AboutUsView from '@/views/AboutUsView.vue';
import ContactView from '@/views/ContactView.vue';
import AHouseLayout from '@/layouts/Admin/AHouseLayout.vue';
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 6eb53bd73f6978779870e3ea383f19f273b70b0d
import HouseView from '@/views/Admin/HouseView.vue';
import ChatListView from '@/views/User/ChatListView.vue';
import ChatView from '@/views/User/ChatView.vue';

<<<<<<< HEAD
=======
import AdminPostsView from '@/components/Admin/AdminPosts.vue';
>>>>>>> origin/staging
=======
>>>>>>> 6eb53bd73f6978779870e3ea383f19f273b70b0d


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
     
    },
 {
  path: 'chatlist',
  name: 'ChatListView',
  component: ChatListView,
  meta: { requiresAuth: true },
 },
    {
      path: 'chatlist/chat/:id',
      name: 'ChatView',
      component: ChatView,
      meta: { requiresAuth: true },
      props: true,
    },
  ],
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
        path: 'houses',
        name: 'AllHouses',
        component: AllHousesView,
        meta: { requiresAuth: true },
      }, 
      {
        path: 'house',
        name: 'House',
        component: HouseView,
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


router.beforeEach(async (to, from) => {
  useAuthStore().initialize()})
  /*
  if (
    // make sure the user is authenticated
    !isAuthenticated &&
    to.name !== 'Login'
  ) {
    // redirect the user to the login page
    return { name: 'Login' }
  }
})
/*
// Global route guard will check the authentication and authorization
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  
  if (to.matched.some((route) => route.meta.requiresAuth)) {
    if (token) {
      const decodedToken = jwt.decode(token);
      if (to.meta.allowedRoles && to.meta.allowedRoles.includes(decodedToken.role)) {
        next(); // User has the required role
      
    }}
     else {
      next('/'); // User is not authenticated; redirect to login
    }
  } else {
    next(); // Allow access to non-protected routes
  }
});
*/
export default router;