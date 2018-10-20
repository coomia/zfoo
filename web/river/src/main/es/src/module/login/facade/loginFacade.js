const loginFacade = {
    path: '/login',
    component: () => import('@/module/login/view/LoginIndex.vue'),
    hidden: true
};

export default loginFacade;
