import storeManager from '@/store/storeManager.js';

const { body } = document;
const WIDTH = 1024;
const RATIO = 3;

export default {
    watch: {
        $route(route) {
            if (this.device === 'mobile' && this.sidebar.opened) {
                storeManager.dispatch('closeSideBar', { withoutAnimation: false });
            }
        }
    },
    beforeMount() {
        window.addEventListener('resize', this.resizeHandler);
    },
    mounted() {
        const isMobile = this.isMobile();
        if (isMobile) {
            storeManager.dispatch('toggleDevice', 'mobile');
            storeManager.dispatch('closeSideBar', { withoutAnimation: true });
        }
    },
    methods: {
        isMobile() {
            const rect = body.getBoundingClientRect();
            return rect.width - RATIO < WIDTH;
        },
        resizeHandler() {
            if (!document.hidden) {
                const isMobile = this.isMobile();
                storeManager.dispatch('toggleDevice', isMobile ? 'mobile' : 'desktop');

                if (isMobile) {
                    storeManager.dispatch('closeSideBar', { withoutAnimation: true });
                }
            }
        }
    }
};
