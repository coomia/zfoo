import Layout from '@/module/layout/Layout';

const zipFacade = {
    path: '/zip',
    component: Layout,
    redirect: '/zip/download',
    alwaysShow: true,
    meta: { title: 'zip', icon: 'zip' },
    children: [
        {
            path: 'download',
            component: () => import('@/module/zip/view/Zip.vue'),
            name: 'ExportZip',
            meta: { title: 'exportZip' }
        }
    ]
};

export default zipFacade;

