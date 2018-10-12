import Vue from 'vue';
import Vuex from 'vuex';
import app from './modules/app';
import errorLog from './modules/errorLog';
import permissionManager from '@/model/permission/manager/permissionManager.js';
import tagsView from './modules/tagsView';
import user from './modules/user';
import getters from './getters';

Vue.use(Vuex);

const storeManager = new Vuex.Store({
    modules: {
        app,
        errorLog,
        permissionManager,
        tagsView,
        user
    },
    getters
});


export default storeManager;
