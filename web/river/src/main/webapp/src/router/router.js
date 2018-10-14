import Vue from 'vue';
import Router from 'vue-router';
import loginFacade from '@/module/login/facade/loginFacade.js';
import clipboardFacade from '@/module/clipboard/facade/clipboardFacade.js';
import permissionFacade from '@/module/permission/facade/permissionFacade.js';
import tabFacade from '@/module/tab/facade/tabFacade.js';
import errorFacade from '@/module/error/facade/errorFacade.js';
import exampleFacade from '@/module/example/facade/exampleFacade.js';
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
    loginFacade,
    // 这个定义的是首页
    clipboardFacade,
    permissionFacade,
    tabFacade,
    exampleFacade,
    errorFacade.error404,
    errorFacade.error401
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
