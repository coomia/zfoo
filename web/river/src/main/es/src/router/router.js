import Vue from 'vue';
import Router from 'vue-router';
import loginFacade from '@/module/login/facade/loginFacade.js';
import dashboardFacade from '@/module/dashboard/facade/dashboardFacade.js';
import clipboardFacade from '@/module/clipboard/facade/clipboardFacade.js';
import permissionFacade from '@/module/permission/facade/permissionFacade.js';
import tabFacade from '@/module/tab/facade/tabFacade.js';
import errorFacade from '@/module/error/facade/errorFacade.js';
import exampleFacade from '@/module/example/facade/exampleFacade.js';
import excelFacade from '@/module/excel/facade/excelFacade.js';
import chartFacade from '@/module/chart/facade/chartFacade.js';
import demoFacade from '@/module/demo/facade/demoFacade.js';
import guideFacade from '@/module/guide/facade/guideFacade.js';
import iconFacade from '@/module/icon/facade/iconFacade.js';
import tableFacade from '@/module/table/facade/tableFacade.js';
import zipFacade from '@/module/zip/facade/zipFacade.js';
import themeFacade from '@/module/theme/facade/themeFacade.js';
import i18nFacade from '@/module/i18n/facade/i18nFacade.js';
import menuFacade from '@/module/menu/facade/menuFacade.js';
import pdfFacade from '@/module/pdf/facade/pdfFacade.js';
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
    roles: ['admin','editor']    will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if true, the page will no be cached(default is false)
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
 **/
// 代表那些不需要动态判断权限的路由，如登录页、404、等通用页面。
export const constantRouterMap = [
    loginFacade,
    // 这个定义的是首页
    dashboardFacade,
    clipboardFacade,
    permissionFacade,
    tabFacade,
    exampleFacade,
    excelFacade,
    chartFacade,
    demoFacade,
    guideFacade,
    iconFacade,
    tableFacade,
    zipFacade,
    themeFacade,
    i18nFacade,
    menuFacade,
    pdfFacade.pdfIndex,
    pdfFacade.pdfDownload,
    errorFacade.error404,
    errorFacade.error401
];

// 代表那些需求动态判断权限并通过 addRouters 动态添加的页面。
export const asyncRouterMap = [
    {
        path: 'external-link',
        component: import('@/module/layout/view/Layout.vue'),
        // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
        // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
        // 若你想不管路由下面的children声明的个数都显示你的根路由，你可以设置alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
        alwaysShow: true,
        meta: {
            title: 'externalLink', // 设置该路由在侧边栏和面包屑中展示的名字
            icon: 'link', // 设置该路由的图标
            roles: ['admin', 'editor'] // 设置该路由进入的权限，支持多个权限叠加
        },
        children: [
            {
                path: 'https://github.com/jaysunxiao/zfoo',
                name: 'WebPageRedirect', // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
                meta: {
                    title: 'link'
                    // if do not set roles, means: this page does not require permission
                }
            }
        ]
    },
    {
        path: '*',
        // 地址从定向：当访问的路径不存在时，从定向到首页
        redirect: '/404',
        // 这个设置有两个作用，当设置 noredirect 的时候该路由在面包屑导航中不可被点击；当配置为一个路径时，如果找不到路径，就从定向component
        // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
        hidden: true
    }
    /** When your routing table is too long, you can split it into small modules**/
];


export default new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
});
