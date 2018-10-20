'use strict';

// 测试方法：node ./test/other/path-test.js
// path包提供了一些用于处理文件路径的小工具
const path = require('path');

// 格式化路径
console.log('normalization : ' + path.normalize('/test/test1//2slashes/1slash/tab/..'));

// 连接路径。该方法的主要用途在于，会正确使用当前系统的路径分隔符，Unix系统是"/"，Windows系统是"\"。
console.log('joint path : ' + path.join('/test', 'test1', '2slashes/1slash', 'tab', '..'));

// 转换为绝对路径。resolve([from ...], to)，将to参数解析为绝对路径。
console.log('resolve : ' + path.resolve(__dirname, '../main.js'));

// 路径中文件的后缀名
console.log('ext name : ' + path.extname('main.js'));
