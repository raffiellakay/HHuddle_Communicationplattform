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


const routes = [
  
  {
    path: '/user',
    name: 'Home',
    component: UHomeLayout,
    children: [
      {
        path: 'home',
        name: 'UserHome',
        component: UHomeView,
      },
      {
        path: 'board',
        name: 'Board',
        component: BoardLayout,
        children: [
          {
            path: 'commonrooms',
            component: CommonRoomsView, 
          },
          {
            path: 'blackboard',
            component: BlackBoardView, 
          },
          {
            path: 'packagefinder',
            component: PackageFinderView,
          },
          {
            path:'search&find',
            component: SearchAndFindView,
          }]
      }]
    },
  {
    path: '/admin',
    name: 'admin',
    component: AHomeLayout,
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: AHomeView
      }, 
      {
        path: 'houses',
        name: 'AllHouses',
        component: AllHousesView
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