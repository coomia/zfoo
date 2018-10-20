import Vue from 'vue';
import VueRouter from 'vue-router';

// Nprogress测试：浏览器地址栏加载进度条测试
import nprogress from 'nprogress';
import 'nprogress/nprogress.css';

// Mock测试：模拟后端返回数据
import '@/model/mock/mock.js';


import App from './App.vue';

Vue.use(VueRouter);

const constantRouterMap = [
    {
        path: '/hello-world',
        component: () => import('@/model/HelloWorld.vue')
    },
    {
        path: '/mock-test',
        component: () => import('@/model/mock/MockTest.vue') // localhost:9527/#/mock-test
    },
    {
        path: '/cookie-test',
        component: () => import('@/model/cookie/CookieTest.vue') // localhost:9527/#/cookie-test
    },
    {
        path: '*',
        redirect: '/hello-world'
    }
];

const router = new VueRouter({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
});

router.beforeEach((to, from, next) => {
    nprogress.start();
    next();
});

router.afterEach((to, from, next) => {
    nprogress.done();
    window.scrollTo(0, 0);
});

new Vue({
    el: '#app',
    router,
    render: h => h(App)
});

// 输出webpack的DefinePlugin插件传递进来的参数
console.log(process.env.a);
console.log(process.env.b);
console.log(process.env.c);
