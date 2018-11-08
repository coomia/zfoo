import Layout from '@/module/layout/view/Layout.vue';

const menuFacade = {
    path: '/menu',
    component: Layout,
    redirect: '/menu/menu1/menu1-1',
    name: 'Menu',
    meta: {
        title: 'menu',
        icon: 'nested'
    },
    children: [
        {
            path: 'menu1',
            component: () => import('@/module/menu/view/menu1/index'), // Parent router-view
            name: 'Menu1',
            meta: { title: 'menu1' },
            redirect: '/menu/menu1/menu1-1',
            children: [
                {
                    path: 'menu1-1',
                    component: () => import('@/module/menu/view//menu1/menu1-1'),
                    name: 'Menu1-1',
                    meta: { title: 'menu1-1' }
                },
                {
                    path: 'menu1-2',
                    component: () => import('@/module/menu/view//menu1/menu1-2'),
                    name: 'Menu1-2',
                    redirect: '/menu/menu1/menu1-2/menu1-2-1',
                    meta: { title: 'menu1-2' },
                    children: [
                        {
                            path: 'menu1-2-1',
                            component: () => import('@/module/menu/view//menu1/menu1-2/menu1-2-1'),
                            name: 'Menu1-2-1',
                            meta: { title: 'menu1-2-1' }
                        },
                        {
                            path: 'menu1-2-2',
                            component: () => import('@/module/menu/view//menu1/menu1-2/menu1-2-2'),
                            name: 'Menu1-2-2',
                            meta: { title: 'menu1-2-2' }
                        }
                    ]
                },
                {
                    path: 'menu1-3',
                    component: () => import('@/module/menu/view//menu1/menu1-3'),
                    name: 'Menu1-3',
                    meta: { title: 'menu1-3' }
                }
            ]
        },
        {
            path: 'menu2',
            name: 'Menu2',
            component: () => import('@/module/menu/view//menu2/index'),
            meta: { title: 'menu2' }
        }
    ]
};

export default menuFacade;
