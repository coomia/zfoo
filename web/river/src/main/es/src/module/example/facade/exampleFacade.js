import Layout from '@/module/layout/view/Layout.vue';

const exampleFacade = {
    path: '/example',
    component: Layout,
    redirect: '/example/list',
    name: 'Example',
    meta: {
        title: 'example',
        icon: 'example'
    },
    children: [
        {
            path: 'create',
            component: () => import('@/module/example/view/CreateForm.vue'),
            name: 'CreateArticle',
            meta: { title: 'createArticle', icon: 'edit' }
        },
        {
            path: 'edit/:id(\\d+)',
            component: () => import('@/module/example/view/EditForm.vue'),
            name: 'EditArticle',
            meta: { title: 'editArticle', noCache: true },
            hidden: true
        },
        {
            path: 'list',
            component: () => import('@/module/example/view/ArticleList.vue'),
            name: 'ArticleList',
            meta: { title: 'articleList', icon: 'list' }
        }
    ]
};

export default exampleFacade;

