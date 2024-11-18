// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Navbar from '@/components/Navbar.vue';
import Home from '@/components/Home.vue';



const routes = [

  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/navbar',
        name: 'Navbar',
        component: Navbar,
      }
    ]
  }

  // Weitere Routen können hier hinzugefügt werden
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;