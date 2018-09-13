Vue.component('tabs', {
    template: '\
        <div class="tabs">\
            <div class="tabs-bar">\
                <div v-for="(item, index) in navList" :class="tabCls(item)" @click="handleChange(index)">\
                    {{item.label}}\
                </div>\
            </div>\
            <div class="tabs-content">\
                <slot></slot>\
            </div>\
        </div>\
    ',

    props: {
        value: {
            type: [String, Number]
        }
    },

    data: function () {
        return {
            currentValue: this.value,
            navList: []
        }
    },

    methods: {
        tabCls: function (item) {
            return ['tabs-tab',
                {
                    'tabs-tab-active': item.name === this.currentValue
                }];
        },

        getTabs: function () {
            // $options代表vue实例的自定义属性
            return this.$children.filter(function (item) {
                return item.$options.name === 'pane';
            });
        },

        updateNav: function () {
            this.navList = [];
            var _this = this;

            // forEach回调函数，第一个代表当前遍历的元素，第二个代表挡墙遍历的元素在数组中的索引，第三个代表包含该元素的数组对象
            this.getTabs().forEach(function (pane, index) {
                    _this.navList.push({
                        label: pane.label,
                        name: pane.name
                    });

                    if (!pane.name) {
                        pane.name = index;
                    }

                    if (index === 0) {
                        if (!_this.currentValue) {
                            _this.currentValue = pane.name;
                        }
                    }
                }
            );


            this.updateStatus();
        },

        updateStatus: function () {
            var tabs = this.getTabs();
            var _this = this;

            tabs.forEach(function (tab) {
                return tab.show = (tab.name === _this.currentValue);
            });
        },

        handleChange: function (index) {
            var nav = this.navList[index];
            var name = nav.name;

            this.currentValue = name;
            this.$emit('input', name);
            this.$emit('on-click', name);
        }
    },

    watch: {
        value: function (newValue, oldValue) {
            this.currentValue = newValue;
        },

        currentValue: function (newValue, oldValue) {
            this.updateStatus();
        }
    },
});