'use strict';
// 测试方法：node ./test/other/node-notifier-test.js
const notifier = require('node-notifier');

// Object
notifier.notify({
    title: 'My notification',
    message: 'Hello, there!',
    icon: 'favicon.png',
    sound: true
});
