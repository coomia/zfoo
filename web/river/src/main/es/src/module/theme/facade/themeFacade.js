import Layout from '@/module/layout/Layout';

const themeFacade = {
    path: '/theme',
    component: Layout,
    redirect: 'noredirect',
    children: [
        {
            path: 'index',
            component: () => import('@/module/theme/view/Theme.vue'),
            name: 'Theme',
            meta: { title: 'theme', icon: 'theme' }
        }
    ]
};

export default themeFacade;

