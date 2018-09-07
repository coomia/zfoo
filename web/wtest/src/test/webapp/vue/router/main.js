import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './app.vue';

Vue.use(VueRouter);

// 在ES6中，使用let和const命令来声明变量和常亮，let和const作用域是块
// 路由配置，path指定当前匹配的路径，component是映射的组件，webpack会把每一个路由都打包为一个js文件，在请求到该页面时，才去异步加载这个页面的js文件
// 异步加载的每个页面的js都交chunk，它的默认名称是0.main.js，1.mian.js···
const Routers = [
    {
        path: '/index',
        meta: {
            title: '首页'
        },
        component: (resolve) => require(['./views/index.vue'], resolve)
    },
    {
        path: '/about',
        meta: {
            title: '关于'
        },
        component: (resolve) => require(['./views/about.vue'], resolve)
    },
    // 路由到动态页面
    {
        path: '/user/:id',
        meta: {
            title: '个人主页'
        },
        component: (resolve) => require(['./views/user.vue'], resolve)
    },
    {
        path: '/user/123/final',
        meta: {
            title: '最后一页'
        },
        component: (resolve) => require(['./views/final.vue'], resolve)
    },
    // 当访问的路径不存在时，从定向到首页
    {
        path: '*',
        redirect: '/index'
    }
];
const RouterConfig = {
    // 使用 HTML5 的 History 路由模式
    mode: 'history',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

// beforeEach和afterEach会在路由改变之前和改变之后触发
router.beforeEach((to, from, next) => {
    window.document.title = to.meta.title;
    next();
});

router.afterEach((to, from, next) => {
    // 滚动条默认是在上一个一面停留的位置，而好的体验肯定是能返回顶端，通过下面的额方法就可以实现
    window.scrollTo(0, 0);
});

new Vue({
    el: '#app',
    router: router,
    render: h => {
        return h(App)
    }

    // 下面语法和上面的等价

    // render: function (h) {
    //     return h(App);
    // }

    // 箭头函数所指的对象就是定义时所在的对象，而不是使用时所在的对象
    // reder: h => {
    //     return h(App);
    // }
});