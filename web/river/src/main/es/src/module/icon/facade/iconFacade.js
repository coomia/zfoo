import Layout from '@/module/layout/view/Layout.vue';

const iconFacade = {
    path: '/icon',
    component: Layout,
    children: [
        {
            path: 'index',
            component: () => import('@/module/icon/view/Icons.vue'),
            name: 'Icons',
            meta: { title: 'icons', icon: 'icon', noCache: true }
        }
    ]
};

export default iconFacade;
