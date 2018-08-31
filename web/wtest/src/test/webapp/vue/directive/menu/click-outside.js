// 全局注册
Vue.directive('click-outside', {

    bind: function (element, binding, vnode) {
        function documentHandler(e) {
            if (element.contains(e.target)) {
                return;
            }

            // 如果v-click-outside后面有表达式，这里表达式应该是一个函数，软后执行这个函数handleColse
            if (binding.expression == null) {
                return;
            }
            binding.value(e);
        }

        element.clickOutside = documentHandler;
        document.addEventListener('click', documentHandler);
        console.log("bind");
    },

    // 只调用一次，指令与元素解绑时调用
    unbind: function (element, binding, vnode) {
        document.removeEventListener('click', element.clickOutside);
        // 删除属性
        delete element.clickOutside;
        console.log("unbind");
    },

    // 被绑定元素插入父节点时调用，父节点存在即可调用，不必存在于document中
    inserted: function (element, binding, vnode) {
        console.log("inserted");
    },

    // 被绑定的元素所在模板更新时调用，不论绑定值是否变化。
    update: function (element, binding, vnode) {
        console.log("update");
    }

});