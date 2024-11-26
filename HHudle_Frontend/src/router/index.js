// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import HomeLayout from '../layouts/HomeLayout.vue';
import LoginLayout from '../layouts/LoginLayout.vue';
import HomeView from '../views/HomeView.vue';
import BoardLayout from '../layouts/BoardLayout.vue';
import BlackBoardView from '@/views/BlackBoardView.vue';
import CommonRoomsView from '@/views/CommonRoomsView.vue';
import PackageFinderView from '@/views/PackageFinderView.vue';
import SearchAndFindView from '@/views/SearchAndFindView.vue';


const routes = [
  { 
    path: '/',
    name: 'login',
    path: LoginLayout
  },

  {
    path: '/',
    name: 'Home',
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'Home',
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
          }

        ]
      }, 
    ],
  },



  // Weitere Routen können hier hinzugefügt werden
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;