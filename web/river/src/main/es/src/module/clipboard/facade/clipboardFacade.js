import Layout from '@/module/layout/Layout';

const clipboardFacade = {
    path: '',
    component: Layout,
    redirect: 'clipboard',
    children: [
        {
            path: 'clipboard',
            component: () => import('@/module/clipboard/view/ClipboardIndex.vue'),
            name: 'ClipboardDemo',
            meta: { title: 'clipboardDemo', icon: 'clipboard', noCache: true }
        }
    ]
};

export default clipboardFacade;
