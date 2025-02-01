// src/router/index.js

import { createRouter, createWebHistory } from 'vue-router';
import { ref } from "vue";

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
import AllBoardsView from '@/views/User/AllBoardsView.vue';
import AboutUsView from '@/views/AboutUsView.vue';
import ContactView from '@/views/ContactView.vue';
import ChatListView from '@/views/User/ChatListView.vue';
import ChatView from '@/views/User/ChatView.vue';
import { useAuthStore } from '@/stores/authStore';
import AHouseView from '@/views/Admin/AHouseView.vue';
import PasswordResetView from '@/views/PasswordResetView.vue';
import SetNewPasswordView from '@/views/SetNewPasswordView.vue';
import AResidentsView from '@/views/Admin/AResidentsView.vue';
import AFacilitiesView from '@/views/Admin/AFacilitiesView.vue';

const routes = [
  //  LOGIN PAGE
  {
    path: '/',
    name: 'Login',
    component: LoginLayout,
    meta: { requiresAuth: false },
  },

  //  USER PAGES
  {
    path: '/user',
    component: UHomeLayout,
    meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
    children: [
      { path: 'home', name: 'UserHome', component: UHomeView },
      { path: 'allboards', name: 'AllBoards', component: AllBoardsView },
      {
        path: 'board',
        name: 'Board',
        component: BoardLayout,
        children: [
          { path: 'commonrooms', name: 'CommonRooms', component: CommonRoomsView },
          { path: 'blackboard', name: 'BlackBoard', component: BlackBoardView },
          { path: 'packagefinder', name: 'PackageFinder', component: PackageFinderView },
          { path: 'search&find', name: 'SearchAndFind', component: SearchAndFindView },
        ],
      },
      { path: 'chatlist', name: 'ChatList', component: ChatListView },
      { path: 'chats/:id', name: 'ChatView', component: ChatView, props: true },
    ],
  },

  //  ADMIN PAGES
  {
    path: '/admin',
    component: AHomeLayout,
    meta: { requiresAuth: true, requiredRoles: ['ROLE_PMANAGEMENT'] },
    children: [
      { path: 'home', name: 'AdminHome', component: AHomeView },
      { path: 'house/:houseId', name: 'House', component: AHouseView, props: true },
      { path: 'house/:houseId/user', name: 'Residents', component: AResidentsView },
      { path: 'house/:houseId/facilities', name: 'Facilities', component: AFacilitiesView },
    ],
  },

  //  PUBLIC PAGES
  { path: '/contact', name: 'Contact', component: ContactView },
  { path: '/aboutUs', name: 'AboutUs', component: AboutUsView },
  { path: '/password-reset', name: 'PasswordReset', component: PasswordResetView },
  { path: '/set-new-password', name: 'SetNewPassword', component: SetNewPasswordView, meta: { requiresAuth: false } },
];

// Create router
const router = createRouter({
  history: createWebHistory(),
  routes,
});


router.beforeEach(async (to, from, next) => {
  await useAuthStore().initialize();

  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const requiredRoles = to.meta.requiredRoles || [];
  const loggedIn = useAuthStore().user;
  let userRoles = loggedIn ? useAuthStore().user.roles : [];

  if (requiresAuth && !loggedIn) {
    return next('/');
  }

  if (requiredRoles.length > 0 && !userRoles.some(role => requiredRoles.includes(role))) {
    return next(loggedIn && userRoles.includes("ROLE_PMANAGEMENT") ? "/admin/home" : "/user/home");
  }

  next();
});

export default router;
