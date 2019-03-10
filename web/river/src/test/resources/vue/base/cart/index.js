var app = new Vue({
    el: '#app',
    data: {
        list: [
            {
                id: 1,
                product: 'iPhone 7',
                price: 6188,
                count: 1
            },
            {
                id: 2,
                product: 'iPad Pro',
                price: 5888,
                count: 1
            },
            {
                id: 3,
                product: 'MacBook Pro',
                price: 21488,
                count: 1
            }
        ]
    },

    methods: {
        reduce: function (index) {
            if (this.list[index] === 1) {
                return;
            }
            this.list[index].count--;
        },
        increase: function (index) {
            this.list[index].count++;
        },
        remove: function (index) {
            // index:要删除数组的起始元素，howmany:要删除的数量
            this.list.splice(index, 1);
        }
    },

    computed: {
        totalPrice: function () {
            var sumPrice = 0;
            for (var i = 0; i < this.list.length; i++) {
                var item = this.list[i];
                sumPrice += item.price * item.count;
            }
            return sumPrice;
        }
    }
})