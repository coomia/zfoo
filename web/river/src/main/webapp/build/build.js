'use strict';
require('./check-versions')();

const Ora = require('ora');
const rm = require('rimraf');
const path = require('path');
const chalk = require('chalk');
const webpack = require('webpack');
const config = require('../config');// require一个文件夹，会优先加载index.js
const webpackConfig = require('./webpack.prod.conf');
const connect = require('connect');
const serveStatic = require('serve-static');

const spinner = new Ora({
    text: 'building for ' + process.env.env_config + ' environment...',
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

// 先删除上次build后的文件，再build
rm(path.join(config.build.assetsRoot, config.build.assetsSubDirectory), err => {
    if (err != null) {
        throw err;
    }

    // 进行打包
    webpack(webpackConfig, (err, stats) => {
        // 打包完成
        spinner.stop();
        if (err) {
            throw err;
        }
        // 输出打包的状态
        process.stdout.write(
            stats.toString({
                colors: true,
                modules: false,
                children: false,
                chunks: false,
                chunkModules: false
            }) + '\n\n'
        );

        // 如果打包出现错误
        if (stats.hasErrors()) {
            console.log(chalk.red(' Build failed with errors.\n'));
            process.exit(1);
        }


        // 打包完成
        console.log(chalk.cyan(' Build complete.\n'));
        console.log(
            chalk.yellow(
                ' Tip: built files are meant to be served over an HTTP server.\n' +
                ' Opening index.html over file:// won\'t work.\n'
            )
        );

        if (process.env.npm_config_preview) {
            const port = 9526;
            const basePath = config.build.assetsPublicPath;
            const app = connect();

            app.use(
                basePath,
                serveStatic('./dist', {
                    index: ['index.html', '/']
                })
            );

            app.listen(port, function() {
                console.log(
                    chalk.green(`> Listening at  http://localhost:${port}${basePath}`)
                );
            });
        }
    });
});
