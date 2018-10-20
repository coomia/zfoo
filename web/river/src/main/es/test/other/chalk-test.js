'use strict';

// 测试方法：node ./test/other/chalk-test.js
// chalk是一个颜色的插件。可以通过chalk.blue(‘hello world’)来改变颜色。
const chalk = require('chalk');
const log = console.log;

// Combine styled and normal strings
log(chalk.blue('Hello') + 'World' + chalk.red('!'));

// Compose multiple styles using the chainable API
log(chalk.blue.bgRed.bold('Hello world!'));

// Pass in multiple arguments
log(chalk.blue('Hello', 'World!', 'Foo', 'bar', 'biz', 'baz'));

// Nest styles
log(chalk.red('Hello', chalk.underline.bgBlue('world') + '!'));

// Nest styles of the same type even (color, underline, background)
log(chalk.green('I am a green line ' + chalk.blue.underline.bold('with a blue substring') +
    ' that becomes green again!'
));
