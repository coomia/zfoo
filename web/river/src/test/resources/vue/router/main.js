import Vue from 'vue';
import VueRouter from 'vue-router';
import Vuex from 'vuex';
import VueBus from './bus/vue-bus';
import Ajax from './ajax/vue-axios'
import App from './app.vue';

import './views/daily/daily-style.css';

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(VueBus);
Vue.use(Ajax);


// ************************************************Router相关配置******************************************************

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
        component: (resolve) => require(['./views/component/final.vue'], resolve)
    },

    // ************************************这个是vuex的测试地址****************************************
    // http://localhost:8080/vuex-test
    {
        path: '/vuex-test',
        meta: {
            title: 'vuex测试'
        },
        component: (resolve) => require(['./views/vuex-test.vue'], resolve)
    },

    // ************************************这个是vue-bus-test的测试地址****************************************
    // http://localhost:8080/vue-bus-test
    {
        path: '/vue-bus-test',
        meta: {
            title: 'vue-bus-test测试'
        },
        component: (resolve) => require(['./views/bus/vue-bus-test.vue'], resolve)
    },

    // ************************************知乎日报和axios的测试地址****************************************
    // http://localhost:8080/main-daily
    {
        path: '/main-daily',
        meta: {
            title: '知乎日报'
        },
        component: (resolve) => require(['./views/daily/main-daily.vue'], resolve)
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


// ************************************************vuex相关配置******************************************************
// 如果数据还有其它组件复用，需要跨多级组件传递数据，需要持久化数据建议放在Vuex；根当前业务有关的建议放在组件内，使用computed属性。
// store包含了应用的数据和操作的过程，只要store数据变化，对应的组件也会立即更新
const store = new Vuex.Store({
    state: {
        count: 0,
        list: [1, 5, 8, 10, 30, 50]
    },
    getters: {
        filteredList: state => {
            return state.list.filter(item => item < 10);
        },
        listCount: (state, getters) => {
            return getters.filteredList.length;
        }
    },

    // 在组件内，来自store的数据只能读取，不能手动改变，改变store中的数据的唯一途径就是显示的提交mutations
    mutations: {
        increment(state, n = 1) {
            state.count += n;
        },
        decrease(state) {
            state.count--;
        }
    },
    actions: {
        increment(context) {
            context.commit('increment');
        },
        asyncIncrement(context) {
            return new Promise(resolve => {
                setTimeout(() => {
                    context.commit('increment');
                    resolve();
                }, 1000)
            });
        }
    }
});


new Vue({
    el: '#app',
    router: router,
    store: store,
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