import Vue from 'vue';
import App from './App';


import 'normalize.css/normalize.css'; // A modern alternative to CSS resets
import ElementUi from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/common/resource/style/index.scss'; // global css
import '@/common/resource/icon'; // icon

import cookies from 'js-cookie';
import router from '@/router/router.js';
import store from '@/store/store.js';

import i18n from '@/common/resource/lang'; // Internationalization
import '@/module/log/service/errorLogService.js'; // error log
import '@/module/permission/service/permissionService.js'; // permission control
import '@/mock/mock.js'; // simulation data
import * as filters from '@/common/filter/filter.js'; // global filters

Vue.use(ElementUi, {
    size: cookies.get('size') || 'medium', // set element-ui default size
    i18n: (key, value) => i18n.t(key, value)
});

// register global utility filters.
Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key]);
});

Vue.config.productionTip = false;


new Vue({
    el: '#app',
    router,
    store,
    i18n,
    render: h => h(App)
});
