// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';


import HomeLayout from '@/layouts/Admin/HomeLayout.vue';
import HomeLayout from '@/layouts/User/HomeLayout.vue';
import HomeView from '@/views/User/HomeView.vue';
import BoardLayout from '@/layouts/User/BoardLayout.vue';
import CommonRoomsView from '@/views/User/CommonRoomsView.vue';
import BlackBoardView from '@/views/User/BlackBoardView.vue';
import PackageFinderView from '@/views/User/PackageFinderView.vue';
import SearchAndFindView from '@/views/User/SearchAndFindView.vue';
import HomeView from '@/views/Admin/HomeView.vue';
import LoginLayout from '@/layouts/LoginLayout.vue';


const routes = [
  
  {
    path: '/user',
    name: 'Home',
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'UserHome',
        component: HomeView,
      },
      {
        path: '/board',
        name: 'Board',
        component: BoardLayout,
        children: [
          {
            path: '/commonrooms',
            component: CommonRoomsView, 
          },
          {
            path: '/blackboard',
            component: BlackBoardView, 
          },
          {
            path: '/packagefinder',
            component: PackageFinderView,
          },
          {
            path:'/search&find',
            component: SearchAndFindView,
          }]
      }]
    },
  {
    path: '/admin',
    name: admin,
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'AdminHome',
        component: HomeView
      }
    ]

  },

  {
    path: '/',
    name: 'Login',
    component: LoginLayout,
  },
  
  
  
  ]
      
  



  // Weitere Routen können hier hinzugefügt werden


const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;