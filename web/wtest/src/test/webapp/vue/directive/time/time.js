// 1分钟以前，显示刚刚
// 1分钟~1小时之间，显示：xx分钟前
// 1小时~1天之间，显示：xx小时前
// 1天~1个月之间，显示：xx天之前
// 大于1个月，显示：xx年xx月xx日
var Time = {

    // 获取当前时间戳
    getUnix: function () {
        return new Date().getTime();
    },

    // 获取今天0点0分0秒的时间戳
    getTodayUnix: function () {
        var date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setMilliseconds(0);
        return date.getTime();
    },

    // 获取今年1月1日0点0分0秒的时间戳
    getYearUnix: function () {
        var date = new Date();
        date.setMonth(0);
        date.setDate(1);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setMilliseconds(0);
        return date.getTime();
    },

    // 获取标准年月日
    getLastDate: function (time) {
        var date = new Date(time);
        var month = ((date.getMonth() + 1) < 10) ? ('0' + (date.getMonth() + 1)) : (date.getMonth() + 1);
        var day = (day.getData() < 10) ? ('0' + date.getDate()) : date.getDate();
        return date.getFullYear() + '-' + month + '-' + day;
    },

    // 转换时间
    getFormatTime: function (timestamp) {
        var now = this.getUnix();
        var today = this.getTodayUnix();
        var year = this.getYearUnix();

        var timeDiff = (now - timestamp) / 1000;

        var tip = '';
        if (timeDiff <= 0) {
            tip = '刚刚';
        } else if (Math.floor(timeDiff / 60) <= 0) {
            tip = '刚刚';
        } else if (timeDiff < 3600) {
            tip = Math.floor(timeDiff / 60) + '分钟前';
        } else if (timeDiff >= 3600 && (timestamp - today) >= 0) {
            tip = Math.floor(timeDiff / 3600) + '小时前';
        } else if (timeDiff / 86400 <= 31) {
            tip = Math.ceil(timeDiff / 86400) + '天前';
        } else {
            tip = this.getLastDate(timestamp);
        }

        return tip;
    }
};

// 全局注册
Vue.directive('time', {

    bind: function (element, binding, vnode) {
        element.innerHTML = Time.getFormatTime(binding.value);
        element.timeout = setInterval(function () {
            element.innerHTML = Time.getFormatTime(binding.value)
        }, 60000);
    },

    unbind: function (element, binding, vnode) {
        clearInterval(element.timeout);
        delete element.timeout;
    },

});


