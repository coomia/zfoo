'use strict';
// 测试方法：在控制台Terminal中用命令 -> node ./test/other/ora-test.js
// 主要用来实现node.js命令行环境的loading效果，和显示各种状态的图标等
const Ora = require('ora');
const spinner = new Ora({
    text: 'Loading unicorns', // Text to display after the spinner
    spinner: {
        'interval': 120,
        'frames': [
            '▹▹▹▹▹',
            '▸▹▹▹▹',
            '▹▸▹▹▹',
            '▹▹▸▹▹',
            '▹▹▹▸▹',
            '▹▹▹▹▸'
        ]
    }
});

spinner.start();

setTimeout(() => {
    spinner.color = 'yellow';
    spinner.text = 'Loading rainbows';
}, 1000);

setTimeout(() => {
    spinner.fail('error');
}, 2000);


setTimeout(() => {
    spinner.succeed('success');
}, 3000);

setTimeout(() => {
    spinner.stop();
}, 4000);
