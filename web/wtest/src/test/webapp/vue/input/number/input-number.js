// 校验正负正数就返回true
function isIntNum(val) {
    if ((val === "") || (val == null) || (isNaN(val))) {
        return false;
    }
    return true;
}

function onUpdateInput(val, obj, event) {
    obj.currentValue = val;
    event.target.value = val;
}


Vue.component('input-number', {
    template: '\
        <div class="input-number">\
            <input type="text" :value="currentValue" @input="handleChange" placeholder="请输入数字"/>\
            <button :disabled="currentValue <= min" @click="handleDown">-</button>\
            <button :disabled="currentValue >= max" @click="handleUp">+</button>\
        </div>\
        ',

    props: {
        max: {
            type: Number,
            default: Infinity
        },

        min: {
            type: Number,
            default: -Infinity
        },

        value: {
            type: Number,
            default: 0
        }
    },

    data: function () {
        return {
            currentValue: this.value
        };
    },

    watch: {},

    methods: {
        handleDown: function () {
            if (this.currentValue <= this.min) {
                return;
            }

            this.currentValue -= 1;
        },

        handleUp: function () {
            if (this.currentValue >= this.max) {
                return;
            }

            this.currentValue += 1;
        },

        handleChange: function (event) {
            // 输入的必须是数字类型
            if (!isIntNum(event.target.value.trim())) {
                // 输入不合法时时重置输入框
                onUpdateInput(this.min, this, event);
                return;
            }

            var val = parseInt(event.target.value.trim());

            if (val > this.max) {
                onUpdateInput(this.max, this, event);
            } else if (val < this.min) {
                onUpdateInput(this.min, this, event);
            } else {
                onUpdateInput(val, this, event);
            }

        },


        updateValue: function (val) {
            if (val > this.max) {
                val = this.max;
            }

            if (val < this.min) {
                val = this.min;
            }

            this.currentValue = val;
        }
    },

    mounted: function () {
        this.updateValue(this.value);
    }
});