import Layout from '@/module/layout/Layout';

const permissionFacade = {
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true, // will always show the root menu
    meta: {
        title: 'permission',
        icon: 'lock',
        roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
        {
            path: 'page',
            component: () => import('@/module/permission/view/PagePermission.vue'),
            name: 'PagePermission',
            meta: {
                title: 'pagePermission',
                roles: ['admin'] // or you can only set roles in sub nav
            }
        },
        {
            path: 'directive',
            component: () => import('@/module/permission/view/DirectivePermission.vue'),
            name: 'DirectivePermission',
            meta: {
                title: 'directivePermission'
                // if do not set roles, means: this page does not require permission
            }
        }
    ]
};

export default permissionFacade;
