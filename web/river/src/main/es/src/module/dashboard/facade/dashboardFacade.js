import Layout from '@/module/layout/view/Layout.vue';

const dashboardFacade = {
    path: '/',
    component: Layout,
    redirect: 'dashboard',
    children: [
        {
            path: 'dashboard',
            component: () => import('@/module/dashboard/view/Dashboard.vue'),
            name: 'Dashboard',
            meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
        }
    ]
};

export default dashboardFacade;
