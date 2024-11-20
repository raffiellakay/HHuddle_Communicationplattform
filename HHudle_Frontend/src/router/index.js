// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import HomeLayout from '../layouts/HomeLayout.vue';
import LoginLayout from '../layouts/LoginLayout.vue';
import TestView from '../views/TestView.vue';
import BoardView from '../views/BoardView.vue';

const routes = [
  {
    path: '',
    name: 'Home',
    component: HomeLayout,
    children: [
      {
        path: '',
        name: 'Test',
        component: TestView,
      },
      {
        path: '/board',
        name: 'Board',
        component: BoardView,
      }
    ],
  },
  {
    path: '/login',
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