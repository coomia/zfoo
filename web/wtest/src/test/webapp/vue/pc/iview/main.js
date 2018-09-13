import Vue from 'vue';
import VueRouter from 'vue-router';
import Vuex from 'vuex';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import App from './app.vue';

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(iView);


// ************************************************Router相关配置******************************************************

const Routers = [
    // http://localhost:8080/cascader-test
    {
        path: '/cascader-test',
        meta: {
            title: 'cascader-test'
        },
        component: (resolve) => require(['./views/cascader-test.vue'], resolve)
    },

    // http://localhost:8080/collapse-test
    {
        path: '/collapse-test',
        meta: {
            title: 'collapse-test'
        },
        component: (resolve) => require(['./views/collapse-test.vue'], resolve)
    }
];
const RouterConfig = {
    mode: 'history',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    window.document.title = to.meta.title;
    next();
});

router.afterEach((to, from, next) => {
    window.scrollTo(0, 0);
});


// ************************************************vuex相关配置******************************************************

const store = new Vuex.Store({
    state: {
        count: 0,
    }
});


new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => {
        return h(App)
    }
});