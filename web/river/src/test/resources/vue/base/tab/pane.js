Vue.component('pane', {
    name: 'pane',

    props: {
        name: {
            type: String
        },
        label: {
            type: String,
            default: ''
        }
    },

    template: '\
        <div class="pane" v-show="show">\
            <slot></slot>\
        </div>\
    ',

    data: function () {
        return {
            show: true
        };
    },

    methods: {
        updateNav: function () {
            this.$parent.updateNav();
        }
    },

    watch: {
        label: function () {
            this.updateNav();
        }
    },

    mounted: function () {
        this.updateNav();
    }

});