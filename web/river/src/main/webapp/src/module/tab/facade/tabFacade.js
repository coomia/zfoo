import Layout from '@/module/layout/Layout';

const tabFacade = {
    path: '/tab',
    component: Layout,
    children: [
        {
            path: 'index',
            component: () => import('@/module/tab/view/TabIndex.vue'),
            name: 'Tab',
            meta: { title: 'tab', icon: 'tab' }
        }
    ]
};

export default tabFacade;
