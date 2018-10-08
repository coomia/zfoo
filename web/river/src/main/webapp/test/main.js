import Vue from 'vue';
import Router from 'vue-router';

import App from './App';

Vue.use(Router);

const constantRouterMap = [
    {
        path: '/hello-world',
        component: () => import('@/model/HelloWorld.vue')
    },
    {
        path: '*',
        redirect: '/hello-world'
    }
];

const router = new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRouterMap
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
