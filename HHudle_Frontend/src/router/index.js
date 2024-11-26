// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import HomeLayout from '../layouts/HomeLayout.vue';
import LoginLayout from '../layouts/LoginLayout.vue';
import HomeView from '../views/HomeView.vue';
import BoardView from '../views/BoardView.vue';

const routes = [
  {
    path: '',
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
        component: BoardView,
      },
      
    ],
  },
  {
    path: '/',
    name: 'Login',
    component: LoginLayout,
  }

  // Weitere Routen können hier hinzugefügt werden
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;