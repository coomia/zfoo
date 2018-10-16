import Vue from 'vue';
import Vuex from 'vuex';
import appManager from '@/module/app/appManager.js';
import errorLogManager from '@/module/log/manager/errorLogManager.js';
import permissionManager from '@/module/permission/manager/permissionManager.js';
import tagsViewManager from '@/module/app/tagsViewManager.js';
import userManager from '@/module/login/manager/userManager.js';

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        appManager,
        errorLogManager,
        permissionManager,
        tagsViewManager,
        userManager
    }
});


export default store;
