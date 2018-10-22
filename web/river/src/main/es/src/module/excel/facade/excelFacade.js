import Layout from '@/module/layout/view/Layout.vue';

const excelFacade = {
    path: '/excel',
    component: Layout,
    redirect: '/excel/export-excel',
    name: 'Excel',
    meta: {
        title: 'excel',
        icon: 'excel'
    },
    children: [
        {
            path: 'export-excel',
            component: () => import('@/module/excel/view/ExportExcel.vue'),
            name: 'ExportExcel',
            meta: { title: 'exportExcel' }
        },
        {
            path: 'export-selected-excel',
            component: () => import('@/module/excel/view/SelectExcel.vue'),
            name: 'SelectExcel',
            meta: { title: 'selectExcel' }
        },
        {
            path: 'upload-excel',
            component: () => import('@/module/excel/view/UploadExcel.vue'),
            name: 'UploadExcel',
            meta: { title: 'uploadExcel' }
        }
    ]
};

export default excelFacade;

