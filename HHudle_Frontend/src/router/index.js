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
import PostCard from '@/components/User/PostCard.vue';




const routes = [
  
  {
    path: '/user',
    name: 'Home',
    component: UHomeLayout,
    meta: { requiresAuth: true   },
    children: [
      {
        path: 'home',
        name: 'UserHome',  //Achtung! Name muss unique sein
        component: UHomeView,
        meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT']  },
      },
      {
        path:'allboards',
        name: 'AllBoards',
        component: AllBoardsView, 
        meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT']  }
      },
      {
        path: 'board',
        name: 'Board',
        component: BoardLayout,
        meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
        children: [
          // Statische Routen zuerst definieren
          {
            path: 'commonrooms',
            name: 'CommonRooms',
            component: CommonRoomsView,
            props: true,
            meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
          },
          {
            path: 'blackboard',
            name: 'BlackBoard',
            component: BlackBoardView,
            props: true,
            meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
          },
          {
            path: 'packagefinder',
            name: 'PackageFinder',
            component: PackageFinderView,
            props: true,
            meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
          },
          {
            path: 'search&find',
            name: 'SearchAndFind',
            component: SearchAndFindView,
            props: true,
            meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
          },
          // Dynamische Route als Fallback für weitere Kategorien
          {
            path: ':category',
            name: 'DynamicCategory',
            component: PostCard, // Generisches Layout für dynamische Kategorien
            props: true,
            meta: { requiresAuth: true, requiredRoles: ['ROLE_RESIDENT'] },
            beforeEnter: (to, from, next) => {
              const allowedCategories = ['blackboard', 'commonrooms', 'packagefinder', 'search&find'];
              if (!allowedCategories.includes(to.params.category)) {
                next('/user/board/blackboard'); // Fallback zu einer Standardroute
              } else {
                next(); // Weiter zur gewünschten Kategorie
              }
            },
          },
        ],
      }]
    },
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
    meta: { requiresAuth: true, requiredRoles: ['ROLE_PMANAGEMENT'] },
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: AHomeView,
        meta: { requiresAuth: true , requiredRoles: ['ROLE_PMANAGEMENT'] },
      }, 
     
      {
        path: 'house/:houseId',
        name: 'House',
        component: AHouseView,
        props: true,
        meta: { requiresAuth: true , requiredRoles: ['ROLE_PMANAGEMENT'] },
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
      }
    ]
  },
  {
    path: '/contact',
    name: 'Contact',
    component: ContactView,
    meta: { requiresAuth: false}
  },
  {
    path: '/aboutUs',
        name: 'AboutUs',
        component: AboutUsView,
        meta: { requiresAuth: false }
  }



  ]
      
  



  // Weitere Routen können hier hinzugefügt werden


const router = createRouter({
  history: createWebHistory(),
  routes,
});



router.beforeEach(async (to, from, next) => {
  await useAuthStore().initialize();
  //secures Websites from not logged in ppl
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const requiresPMRole = to.matched.some((record) =>
    record.meta.requiredRoles?.includes("ROLE_PMANAGEMENT")
  );
  const requiresResidentRole = to.matched.some((record) =>
    record.meta.requiredRoles?.includes("ROLE_RESIDENT")
  );
  let userRoles = [];
  if (useAuthStore().user) {
    userRoles = useAuthStore().user.roles;
  }
  const loggedIn = useAuthStore().user;
  if (requiresAuth && !loggedIn) {
    next("/");
  } else {
    if (!requiresPMRole && !requiresResidentRole) {
      next();
      return;
    }

    if (
      requiresPMRole &&
      userRoles.find((role) => role === "ROLE_PMANAGEMENT")
    ) {
      next();
      return;
    }
    if (
      requiresResidentRole &&
      userRoles.find((role) => role === "ROLE_RESIDENT")
    ) {
      next();
      return;
    }

    const rolePmanagement = userRoles.find(
      (role) => role === "ROLE_PMANAGEMENT"
    );
    const roleResident = userRoles.find((role) => role === "ROLE_RESIDENT");
    console.log(rolePmanagement);
    console.log(roleResident);

    if (rolePmanagement) {
      next("/admin/home");
      return;
    }
    if (roleResident) {
      next("/user/home");
      return;
    }
    next();
  }

  
});

export default router;