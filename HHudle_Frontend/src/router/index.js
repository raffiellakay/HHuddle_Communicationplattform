// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import HomeLayout from '@/layouts/User/HomeLayout.vue';
import LoginLayout from '../layouts/LoginLayout.vue';
import HomeView from '../views/User/HomeView.vue';
import BoardLayout from '../layouts/User/BoardLayout.vue';
import BlackBoardView from '@/views/User/BlackBoardView.vue';
import CommonRoomsView from '@/views/User/CommonRoomsView.vue';
import PackageFinderView from '@/views/User/PackageFinderView.vue';
import SearchAndFindView from '@/views/User/SearchAndFindView.vue';


const routes = [
  
  {
    path: '/',
    name: 'Home',
    component: HomeLayout,
    children: [
      {
        path: '/home',
        name: 'Home',
        component: HomeView,
      }]
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
    ]
 

  



  // Weitere Routen können hier hinzugefügt werden


const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;