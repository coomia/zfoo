'use strict';

// 测试方法：node ./test/other/port-finder-test.js
// portfinder提供了查找可用端口的方法
const portfinder = require('portfinder');

// 一旦8080端口被占用，会依次寻找8080+1,依次类推...8080+n，直到找到为止。找到最后也找不到，err就不为空了
portfinder.basePort = 8080;
portfinder.getPort((err, port) => {
    if (err != null) {
        console.log('Could not get a free ,' + 'port:' + port + ' `err` contains the reason.');
    } else {
        console.log('port:' + port + ' is guaranteed to be a free port in this scope.');
    }
});
