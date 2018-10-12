import Vue from 'vue';
import Router from 'vue-router';
/* Layout */
import Layout from '@/model/layout/Layout';
/* Router Modules */
// import componentsRouter from './modules/components';
// import chartsRouter from './modules/charts';
// import tableRouter from './modules/table';
// import nestedRouter from './modules/nested';

Vue.use(Router);

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
 **/
export const constantRouterMap = [
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path*',
                component: () => import('@/model/redirect/index')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/model/login/index'),
        hidden: true
    },
    {
        path: '/auth-redirect',
        component: () => import('@/model/login/authredirect'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/model/error/view/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/model/error/view/401'),
        hidden: true
    },
    {
        path: '',
        component: Layout,
        redirect: 'clipboard',
        children: [
            {
                path: 'clipboard',
                component: () => import('@/model/clipboard/index.vue'),
                name: 'ClipboardDemo',
                meta: { title: 'clipboardDemo', icon: 'clipboard', noCache: true }
            }
        ]
    }
];

export default new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
});

export const asyncRouterMap = [
    { path: '*', redirect: '/404', hidden: true }
    /** When your routing table is too long, you can split it into small modules**/
    // componentsRouter,
    // chartsRouter,
    // nestedRouter,
    // tableRouter,
];
