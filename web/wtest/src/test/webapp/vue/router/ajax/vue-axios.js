const install = function(Vue) {
    const Ajax = new Vue({
        methods: {
            emit(event, ...args) {
                this.$emit(event, ...args);
            },
            on(event, callback) {
                this.$on(event, callback);
            },
            off(event, callback) {
                this.$off(event, callback);
            }
        }
    });
    Vue.prototype.$ajax = Ajax;
};

export default install;
