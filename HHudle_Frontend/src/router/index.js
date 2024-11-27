// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import UserHomeLayout from '../layouts/User/UserHomeLayout.vue';
import LoginLayout from '../layouts/LoginLayout.vue';
import AdminHomeLayout from '../layouts/Admin/AdminHomeLayout.vue'; 


import UserHomeView from '../views/User/UserHomeView.vue';
import BoardView from '../views/User/BoardView.vue';
import AdminHomeView from '../views/Admin/AdminHomeView.vue';

const routes = [
  {
    path: '/user',
    name: 'Home',
    component: UserHomeLayout,
    children: [
      {
        path: 'home',
        name: 'UserHome',  //Achtung! Name muss unique sein
        component: UserHomeView,
      },
      {
        path: 'board',
        name: 'Board',
        component: BoardView,
      },
      
    ],
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminHomeLayout,
    children: [
      {
        path: '/home',
        name: 'AdminHome',
        component: AdminHomeView,
      },
     
    ],
  },
  {
    path: '/',
    name: 'Login',
    component: LoginLayout,
  },
 


  // Weitere Routen können hier hinzugefügt werden
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;