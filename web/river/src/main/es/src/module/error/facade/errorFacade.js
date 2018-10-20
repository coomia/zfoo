const errorFacade = {
    error404: {
        path: '/404',
        component: () => import('@/module/error/view/404'),
        hidden: true
    },
    error401: {
        path: '/401',
        component: () => import('@/module/error/view/401'),
        hidden: true
    }
};

export default errorFacade;
