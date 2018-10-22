import Layout from '@/module/layout/view/Layout.vue';

const guideFacade = {
    path: '/guide',
    component: Layout,
    redirect: '/guide/index',
    children: [
        {
            path: 'index',
            component: () => import('@/module/guide/view/Guide.vue'),
            name: 'Guide',
            meta: { title: 'guide', icon: 'guide', noCache: true }
        }
    ]
};

export default guideFacade;
