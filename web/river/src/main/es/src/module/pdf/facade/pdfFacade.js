import Layout from '@/module/layout/view/Layout.vue';

const pdfFacade = {
    pdfIndex: {
        path: '/pdf',
        component: Layout,
        redirect: '/pdf/index',
        meta: { title: 'PDF', icon: 'pdf' },
        children: [
            {
                path: 'index',
                component: () => import('@/module/pdf/view/Index.vue'),
                name: 'PDF',
                meta: { title: 'PDF' }
            }
        ]
    },
    pdfDownload: {
        path: '/pdf/download',
        component: () => import('@/module/pdf/view/Download.vue'),
        hidden: true
    }
};

export default pdfFacade;

