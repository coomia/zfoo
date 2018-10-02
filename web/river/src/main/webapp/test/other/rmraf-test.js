'use strict';
// 测试方法：进入到这个js文件所在的文件夹，新建test.txt，在控制台Terminal中用命令 -> node ./rmraf-test.js
// rimraf包的作用用来删除文件和文件夹，不管文件夹是否为空，都可删除
const rimraf = require('rimraf');
rimraf('./test.txt', function(err) { // 删除当前目录下的 test.txt
    console.log(err);
});
