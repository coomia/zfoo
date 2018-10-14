import router from '@/router/router.js';
import storeManager from '@/store/storeManager.js';
import { Message } from 'element-ui';
import NProgress from 'nprogress'; // progress bar
import 'nprogress/nprogress.css'; // progress bar style
import { getToken } from '@/utils/authUtils.js'; // getToken from cookie

NProgress.configure({ showSpinner: false });// NProgress Configuration

// permission judge function
function hasPermission(roles, permissionRoles) {
    if (roles.indexOf('admin') >= 0) return true; // admin permission passed directly
    if (!permissionRoles) return true;
    return roles.some(role => permissionRoles.indexOf(role) >= 0);
}

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/DirectivePermission.vue
 */
export function checkPermission(value) {
    if (value && value instanceof Array && value.length > 0) {
        const roles = storeManager.getters && storeManager.getters.roles;
        const permissionRoles = value;

        const hasPermission = roles.some(role => {
            return permissionRoles.includes(role);
        });

        if (!hasPermission) {
            return false;
        }
        return true;
    } else {
        console.error(`need roles! Like v-permission="['admin','editor']"`);
        return false;
    }
}

const whiteList = ['/login'];// no redirect whitelist

router.beforeEach((to, from, next) => {
    NProgress.start(); // start progress bar
    if (getToken()) { // determine if there has token
        /* has token*/
        if (to.path === '/login') {
            next({ path: '/' });
            NProgress.done(); // if current page is dashboard will not trigger	afterEach hook, so manually handle it
        } else {
            if (storeManager.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
                storeManager.dispatch('GetUserInfo').then(res => { // 拉取user_info
                    const roles = res.data.roles; // note: roles must be a array! such as: ['editor','develop']
                    storeManager.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
                        router.addRoutes(storeManager.getters.addRouters); // 动态添加可访问路由表
                        next({ ...to, replace: true }); // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
                    });
                }).catch((err) => {
                    storeManager.dispatch('FedLogOut').then(() => {
                        Message.error(err || 'Verification failed, please login again');
                        next({ path: '/' });
                    });
                });
            } else {
                // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
                if (hasPermission(storeManager.getters.roles, to.meta.roles)) {
                    next();
                } else {
                    next({ path: '/401', replace: true, query: { noGoBack: true }});
                }
                // 可删 ↑
            }
        }
    } else {
        /* has no token*/
        if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next();
        } else {
            next(`/login?redirect=${to.path}`); // 否则全部重定向到登录页
            NProgress.done(); // if current page is login will not trigger afterEach hook, so manually handle it
        }
    }
});

router.afterEach(() => {
    NProgress.done(); // finish progress bar
});
