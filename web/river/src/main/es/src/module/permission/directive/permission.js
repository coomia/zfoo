import store from '@/store/store.js';

const permission = {
    inserted(el, binding, vnode) {
        const { value } = binding;
        const roles = store.getters && store.getters.roles;

        if (value && value instanceof Array && value.length > 0) {
            const permissionRoles = value;

            const hasPermission = roles.some(role => {
                return permissionRoles.includes(role);
            });

            if (!hasPermission) {
                el.parentNode && el.parentNode.removeChild(el);
            }
        } else {
            throw new Error(`need roles! Like v-permission="['admin','editor']"`);
        }
    }
};


const install = function(Vue) {
    Vue.directive('permission', permission);
};

if (window.Vue) {
    window['permission'] = permission;
    Vue.use(install); // eslint-disable-line
}

permission.install = install;

export default permission;
