'use strict';
// Template version: 1.2.6
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path');

module.exports = {
    dev: {
        // 编译输出的二级目录
        assetsSubDirectory: 'static',
        // 编译发布的根目录，可配置为资源服务器域名或者cdn域名
        assetsPublicPath: '/',
        proxyTable: {},

        // Various Dev Server settings

        // can be overwritten by process.env.HOST
        // if you want dev by ip, please set host: '0.0.0.0'
        host: 'localhost',
        port: 9527, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
        autoOpenBrowser: true,
        // 下面两个都是浏览器展示错误的方式
        errorOverlay: true, // 在浏览器是否展示错误蒙层
        notifyOnErrors: false, // 是否展示错误的通知
        poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

        // Use Eslint Loader?
        // If true, your code will be linted during bundling and
        // linting errors and warnings will be shown in the console.
        useEslint: true,
        // If true, eslint errors and warnings will also be shown in the error overlay
        // in the browser.
        showEslintErrorsInOverlay: false, // 如果设置为true，在浏览器中，eslint的错误和警告会以蒙层的方式展现。

        /**
         * Source Maps
         */

        // https://webpack.js.org/configuration/devtool/#development
        devtool: 'cheap-source-map',

        // CSS Sourcemaps off by default because relative paths are "buggy"
        // with this option, according to the CSS-Loader README
        // (https://github.com/webpack/css-loader#sourcemaps)
        // In our experience, they generally work as expected,
        // just be aware of this issue when enabling this option.
        cssSourceMap: false
    },

    build: {
        // html文件的生成的地方
        index: path.resolve(__dirname, '../dist/index.html'),

        // Paths
        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',

        /**
         * You can set by youself according to actual condition
         * You will need to set this if you plan to deploy your site under a sub path,
         * for example GitHub pages. If you plan to deploy your site to https://foo.github.io/bar/,
         * then assetsPublicPath should be set to "/bar/".
         * In most cases please use '/' !!!
         */
        assetsPublicPath: '/',

        /**
         * Source Maps
         */
        productionSourceMap: false,
        // https://webpack.js.org/configuration/devtool/#production
        devtool: 'source-map',

        // Gzip off by default as many popular static hosts such as
        // Surge or Netlify already gzip all static assets for you.
        // Before setting to `true`, make sure to:
        // npm install --save-dev compression-webpack-plugin
        productionGzip: false,
        productionGzipExtensions: ['js', 'css'],

        // Run the build command with an extra argument to
        // View the bundle analyzer report after build finishes: `npm run build:prod --report`
        // Set to `true` or `false` to always turn it on or off
        // ebpack-bundle-analyzer 一个模块分析的东西，可以装逼
        // "build:sit": "cross-env NODE_ENV=production env_config=sit npm_config_preview=true  npm_config_report=true node build/build.js"
        bundleAnalyzerReport: process.env.npm_config_report || false,

        // `npm run build:prod --generate_report`
        generateAnalyzerReport: process.env.npm_config_generate_report || false
    }
};
