import Layout from '@/module/layout/Layout';


const i18nFacade = {
    path: '/i18n',
    component: Layout,
    children: [
        {
            path: 'index',
            component: () => import('@/module/i18n/view/I18n.vue'),
            name: 'I18n',
            meta: { title: 'i18n', icon: 'international' }
        }
    ]
};

export default i18nFacade;
