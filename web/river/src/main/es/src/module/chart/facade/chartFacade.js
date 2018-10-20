import Layout from '@/module/layout/Layout';

const chartFacade = {
    path: '/chart',
    component: Layout,
    redirect: 'noredirect',
    name: 'Charts',
    meta: {
        title: 'charts',
        icon: 'chart'
    },
    children: [
        {
            path: 'keyboard',
            component: () => import('@/module/chart/view/Keyboard.vue'),
            name: 'KeyboardChart',
            meta: { title: 'keyboardChart', noCache: true }
        },
        {
            path: 'line',
            component: () => import('@/module/chart/view/Line.vue'),
            name: 'LineChart',
            meta: { title: 'lineChart', noCache: true }
        },
        {
            path: 'mixchart',
            component: () => import('@/module/chart/view/MixChart.vue'),
            name: 'MixChart',
            meta: { title: 'mixChart', noCache: true }
        }
    ]
};

export default chartFacade;
