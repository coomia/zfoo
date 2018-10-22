import Layout from '@/module/layout/view/Layout.vue';

const clipboardFacade = {
    path: '/clipboard',
    component: Layout,
    redirect: 'clipboard',
    children: [
        {
            path: 'index',
            component: () => import('@/module/clipboard/view/ClipboardIndex.vue'),
            name: 'Clipboard',
            meta: { title: 'clipboard', icon: 'clipboard', noCache: true }
        }
    ]
};

export default clipboardFacade;
